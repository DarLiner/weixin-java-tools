package me.chanjar.weixin.mp.api.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.joor.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.Utf8ResponseHandler;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpPayService;
import me.chanjar.weixin.mp.bean.pay.WxMpPayCallback;
import me.chanjar.weixin.mp.bean.pay.WxMpPayRefundResult;
import me.chanjar.weixin.mp.bean.pay.WxMpPayResult;
import me.chanjar.weixin.mp.bean.pay.WxRedpackResult;
import me.chanjar.weixin.mp.bean.pay.WxSendRedpackRequest;
import me.chanjar.weixin.mp.bean.result.WxMpPrepayIdResult;

/**
 * Created by Binary Wang on 2016/7/28.
 *
 * @author binarywang (https://github.com/binarywang)
 */
public class WxMpPayServiceImpl implements WxMpPayService {

  private final Logger log = LoggerFactory.getLogger(WxMpPayServiceImpl.class);
  private final String[] REQUIRED_ORDER_PARAMETERS = new String[]{"appid",
          "mch_id", "body", "out_trade_no", "total_fee", "spbill_create_ip",
          "notify_url", "trade_type"};
  private HttpHost httpProxy;
  private WxMpServiceImpl wxMpService;

  public WxMpPayServiceImpl(WxMpServiceImpl wxMpService) {
    this.wxMpService = wxMpService;
    this.httpProxy = wxMpService.getHttpProxy();
  }

  @Override
  public WxMpPrepayIdResult getPrepayId(String openId, String outTradeNo,
                                        double amt, String body, String tradeType, String ip,
                                        String callbackUrl) {
    Map<String, String> packageParams = new HashMap<>();
    packageParams.put("appid",
            this.wxMpService.getWxMpConfigStorage().getAppId());
    packageParams.put("mch_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());
    packageParams.put("body", body);
    packageParams.put("out_trade_no", outTradeNo);
    packageParams.put("total_fee", (int) (amt * 100) + "");
    packageParams.put("spbill_create_ip", ip);
    packageParams.put("notify_url", callbackUrl);
    packageParams.put("trade_type", tradeType);
    packageParams.put("openid", openId);

    return getPrepayId(packageParams);
  }

  @Override
  public WxMpPrepayIdResult getPrepayId(final Map<String, String> parameters) {
    final SortedMap<String, String> packageParams = new TreeMap<>(parameters);
    packageParams.put("appid",
            this.wxMpService.getWxMpConfigStorage().getAppId());
    packageParams.put("mch_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());
    packageParams.put("nonce_str", System.currentTimeMillis() + "");
    checkParameters(packageParams);

    String sign = this.createSign(packageParams,
            this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    packageParams.put("sign", sign);

    StringBuilder request = new StringBuilder("<xml>");
    for (Map.Entry<String, String> para : packageParams.entrySet()) {
      request.append(String.format("<%s>%s</%s>", para.getKey(),
              para.getValue(), para.getKey()));
    }

    request.append("</xml>");

    HttpPost httpPost = new HttpPost(
            "https://api.mch.weixin.qq.com/pay/unifiedorder");
    if (this.httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(this.httpProxy)
              .build();
      httpPost.setConfig(config);
    }

    StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response = this.wxMpService.getHttpclient()
            .execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE
              .handleResponse(response);
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxMpPrepayIdResult.class);
      return (WxMpPrepayIdResult) xstream.fromXML(responseContent);
    } catch (IOException e) {
      throw new RuntimeException("Failed to get prepay id due to IO exception.",
              e);
    } finally {
      httpPost.releaseConnection();
    }
  }

  private void checkParameters(Map<String, String> parameters) {
    for (String para : this.REQUIRED_ORDER_PARAMETERS) {
      if (!parameters.containsKey(para)) {
        throw new IllegalArgumentException(
                "Reqiured argument '" + para + "' is missing.");
      }
    }

    if ("JSAPI".equals(parameters.get("trade_type"))
            && !parameters.containsKey("openid")) {
      throw new IllegalArgumentException(
              "Reqiured argument 'openid' is missing when trade_type is 'JSAPI'.");
    }

    if ("NATIVE".equals(parameters.get("trade_type"))
            && !parameters.containsKey("product_id")) {
      throw new IllegalArgumentException(
              "Reqiured argument 'product_id' is missing when trade_type is 'NATIVE'.");
    }
  }

  @Override
  public Map<String, String> getJsapiPayInfo(String openId, String outTradeNo,
                                             double amt, String body, String ip, String callbackUrl)
          throws WxErrorException {
    Map<String, String> packageParams = new HashMap<>();
    packageParams.put("appid",
            this.wxMpService.getWxMpConfigStorage().getAppId());
    packageParams.put("mch_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());
    packageParams.put("body", body);
    packageParams.put("out_trade_no", outTradeNo);
    packageParams.put("total_fee", (int) (amt * 100) + "");
    packageParams.put("spbill_create_ip", ip);
    packageParams.put("notify_url", callbackUrl);
    packageParams.put("trade_type", "JSAPI");
    packageParams.put("openid", openId);

    return getPayInfo(packageParams);
  }

  @Override
  public Map<String, String> getNativePayInfo(String productId,
                                              String outTradeNo, double amt, String body, String ip, String callbackUrl)
          throws WxErrorException {
    Map<String, String> packageParams = new HashMap<>();
    packageParams.put("appid",
            this.wxMpService.getWxMpConfigStorage().getAppId());
    packageParams.put("mch_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());
    packageParams.put("body", body);
    packageParams.put("out_trade_no", outTradeNo);
    packageParams.put("total_fee", (int) (amt * 100) + "");
    packageParams.put("spbill_create_ip", ip);
    packageParams.put("notify_url", callbackUrl);
    packageParams.put("trade_type", "NATIVE");
    packageParams.put("product_id", productId);

    return getPayInfo(packageParams);
  }

  @Override
  public Map<String, String> getPayInfo(Map<String, String> parameters)
          throws WxErrorException {
    WxMpPrepayIdResult wxMpPrepayIdResult = getPrepayId(parameters);

    if (!"SUCCESS".equalsIgnoreCase(wxMpPrepayIdResult.getReturn_code())
            || !"SUCCESS".equalsIgnoreCase(wxMpPrepayIdResult.getResult_code())) {
      WxError error = new WxError();
      error.setErrorCode(-1);
      error.setErrorMsg("return_code:" + wxMpPrepayIdResult.getReturn_code()
              + ";return_msg:" + wxMpPrepayIdResult.getReturn_msg()
              + ";result_code:" + wxMpPrepayIdResult.getResult_code() + ";err_code"
              + wxMpPrepayIdResult.getErr_code() + ";err_code_des"
              + wxMpPrepayIdResult.getErr_code_des());
      throw new WxErrorException(error);
    }

    String prepayId = wxMpPrepayIdResult.getPrepay_id();
    if (prepayId == null || prepayId.equals("")) {
      throw new RuntimeException(
              String.format("Failed to get prepay id due to error code '%s'(%s).",
                      wxMpPrepayIdResult.getErr_code(),
                      wxMpPrepayIdResult.getErr_code_des()));
    }

    Map<String, String> payInfo = new HashMap<>();
    payInfo.put("appId", this.wxMpService.getWxMpConfigStorage().getAppId());
    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
    payInfo.put("nonceStr", System.currentTimeMillis() + "");
    payInfo.put("package", "prepay_id=" + prepayId);
    payInfo.put("signType", "MD5");
    if ("NATIVE".equals(parameters.get("trade_type"))) {
      payInfo.put("codeUrl", wxMpPrepayIdResult.getCode_url());
    }

    String finalSign = this.createSign(payInfo,
            this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    payInfo.put("paySign", finalSign);
    return payInfo;
  }

  @Override
  public WxMpPayResult getJSSDKPayResult(String transactionId,
                                         String outTradeNo) {
    String nonce_str = System.currentTimeMillis() + "";

    SortedMap<String, String> packageParams = new TreeMap<>();
    packageParams.put("appid",
            this.wxMpService.getWxMpConfigStorage().getAppId());
    packageParams.put("mch_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());

    if (transactionId != null && !"".equals(transactionId.trim())) {
      packageParams.put("transaction_id", transactionId);
    } else if (outTradeNo != null && !"".equals(outTradeNo.trim())) {
      packageParams.put("out_trade_no", outTradeNo);
    } else {
      throw new IllegalArgumentException(
              "Either 'transactionId' or 'outTradeNo' must be given.");
    }

    packageParams.put("nonce_str", nonce_str);
    packageParams.put("sign", this.createSign(packageParams,
            this.wxMpService.getWxMpConfigStorage().getPartnerKey()));

    StringBuilder request = new StringBuilder("<xml>");
    for (Map.Entry<String, String> para : packageParams.entrySet()) {
      request.append(String.format("<%s>%s</%s>", para.getKey(),
              para.getValue(), para.getKey()));
    }
    request.append("</xml>");

    HttpPost httpPost = new HttpPost(
            "https://api.mch.weixin.qq.com/pay/orderquery");
    if (this.httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(this.httpProxy)
              .build();
      httpPost.setConfig(config);
    }

    StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response = this.wxMpService.getHttpclient()
            .execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE
              .handleResponse(response);
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxMpPayResult.class);
      return (WxMpPayResult) xstream.fromXML(responseContent);
    } catch (IOException e) {
      throw new RuntimeException("Failed to query order due to IO exception.",
              e);
    }
  }

  @Override
  public WxMpPayCallback getJSSDKCallbackData(String xmlData) {
    try {
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxMpPayCallback.class);
      return (WxMpPayCallback) xstream.fromXML(xmlData);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new WxMpPayCallback();
  }

  @Override
  public WxMpPayRefundResult refundPay(Map<String, String> parameters)
          throws WxErrorException {
    SortedMap<String, String> refundParams = new TreeMap<>(parameters);
    refundParams.put("appid",
            this.wxMpService.getWxMpConfigStorage().getAppId());
    refundParams.put("mch_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());
    refundParams.put("nonce_str", System.currentTimeMillis() + "");
    refundParams.put("op_user_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());
    String sign = this.createSign(refundParams,
            this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    refundParams.put("sign", sign);

    StringBuilder request = new StringBuilder("<xml>");
    for (Map.Entry<String, String> para : refundParams.entrySet()) {
      request.append(String.format("<%s>%s</%s>", para.getKey(),
              para.getValue(), para.getKey()));
    }
    request.append("</xml>");

    HttpPost httpPost = new HttpPost(
            "https://api.mch.weixin.qq.com/secapi/pay/refund");
    if (this.httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(this.httpProxy)
              .build();
      httpPost.setConfig(config);
    }

    StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response = this.wxMpService.getHttpclient()
            .execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE
              .handleResponse(response);
      XStream xstream = XStreamInitializer.getInstance();
      xstream.processAnnotations(WxMpPayRefundResult.class);
      WxMpPayRefundResult wxMpPayRefundResult = (WxMpPayRefundResult) xstream
              .fromXML(responseContent);

      if (!"SUCCESS".equalsIgnoreCase(wxMpPayRefundResult.getResultCode())
              || !"SUCCESS".equalsIgnoreCase(wxMpPayRefundResult.getReturnCode())) {
        WxError error = new WxError();
        error.setErrorCode(-1);
        error.setErrorMsg("return_code:" + wxMpPayRefundResult.getReturnCode()
                + ";return_msg:" + wxMpPayRefundResult.getReturnMsg()
                + ";result_code:" + wxMpPayRefundResult.getResultCode()
                + ";err_code" + wxMpPayRefundResult.getErrCode() + ";err_code_des"
                + wxMpPayRefundResult.getErrCodeDes());
        throw new WxErrorException(error);
      }

      return wxMpPayRefundResult;
    } catch (IOException e) {
      String message = MessageFormatter
              .format("Exception happened when sending refund '{}'.",
                      request.toString())
              .getMessage();
      this.log.error(message, e);
      throw new WxErrorException(
              WxError.newBuilder().setErrorMsg(message).build());
    } finally {
      httpPost.releaseConnection();
    }
  }

  @Override
  public boolean checkJSSDKCallbackDataSignature(Map<String, String> kvm,
                                                 String signature) {
    return signature.equals(this.createSign(kvm,
            this.wxMpService.getWxMpConfigStorage().getPartnerKey()));
  }

  @Override
  @Deprecated
  public WxRedpackResult sendRedpack(Map<String, String> parameters)
          throws WxErrorException {
    SortedMap<String, String> packageParams = new TreeMap<>(parameters);
    packageParams.put("wxappid",
            this.wxMpService.getWxMpConfigStorage().getAppId());
    packageParams.put("mch_id",
            this.wxMpService.getWxMpConfigStorage().getPartnerId());
    packageParams.put("nonce_str", System.currentTimeMillis() + "");

    String sign = this.createSign(packageParams,
            this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    packageParams.put("sign", sign);

    StringBuilder request = new StringBuilder("<xml>");
    for (Map.Entry<String, String> para : packageParams.entrySet()) {
      request.append(String.format("<%s>%s</%s>", para.getKey(),
              para.getValue(), para.getKey()));
    }

    request.append("</xml>");

    HttpPost httpPost = new HttpPost(
            "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
    if (this.httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(this.httpProxy)
              .build();
      httpPost.setConfig(config);
    }

    StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response = this.wxMpService.getHttpclient()
            .execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE
              .handleResponse(response);
      XStream xstream = XStreamInitializer.getInstance();
      xstream.processAnnotations(WxRedpackResult.class);
      return (WxRedpackResult) xstream.fromXML(responseContent);
    } catch (IOException e) {
      String message = MessageFormatter
              .format("Exception occured when sending redpack '{}'.",
                      request.toString())
              .getMessage();
      this.log.error(message, e);
      throw new WxErrorException(WxError.newBuilder().setErrorMsg(message).build());
    } finally {
      httpPost.releaseConnection();
    }
  }

  @Override
  public WxRedpackResult sendRedpack(WxSendRedpackRequest request) throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxSendRedpackRequest.class);
    xstream.processAnnotations(WxRedpackResult.class);

    request.setWxAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(xmlBean2Map(request),
        this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
    if (request.getAmtType() != null) {
      //裂变红包
      url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack";
    }

    String responseContent = this.wxMpService.post(url, xstream.toXML(request));
    WxRedpackResult redpackResult = (WxRedpackResult) xstream.fromXML(responseContent);
    if("FAIL".equals(redpackResult.getResultCode())){
      throw new WxErrorException(
          WxError.newBuilder().setErrorMsg(redpackResult.getErrCode() + ":" + redpackResult.getErrCodeDes()).build());
    }

    return redpackResult;
  }

  private Map<String, String> xmlBean2Map(Object bean) {
    Map<String, String> result = Maps.newHashMap();
    for (Entry<String, Reflect> entry : Reflect.on(bean).fields().entrySet()) {
      Reflect reflect = entry.getValue();
      if (reflect.get() == null) {
        continue;
      }

      try {
        Field field = WxSendRedpackRequest.class.getDeclaredField(entry.getKey());
        if (field.isAnnotationPresent(XStreamAlias.class)) {
          result.put(field.getAnnotation(XStreamAlias.class).value(), reflect.get().toString());
        }
      } catch (NoSuchFieldException | SecurityException e) {
        e.printStackTrace();
      }

    }

    return result;
  }

  /**
   * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3)
   * @param packageParams 原始参数
   * @param signKey 加密Key(即 商户Key)
   * @return 签名字符串
   */
  private String createSign(Map<String, String> packageParams, String signKey) {
    SortedMap<String, String> sortedMap = new TreeMap<>(packageParams);

    StringBuffer toSign = new StringBuffer();
    for (String key : sortedMap.keySet()) {
      String value = packageParams.get(key);
      if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
        toSign.append(key + "=" + value + "&");
      }
    }

    toSign.append("key=" + signKey);

    return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
  }

}
