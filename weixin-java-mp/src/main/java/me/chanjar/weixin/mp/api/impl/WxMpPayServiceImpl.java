package me.chanjar.weixin.mp.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.joor.Reflect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import me.chanjar.weixin.common.annotation.Required;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpPayService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.pay.WxEntPayRequest;
import me.chanjar.weixin.mp.bean.pay.WxEntPayResult;
import me.chanjar.weixin.mp.bean.pay.WxMpPayCallback;
import me.chanjar.weixin.mp.bean.pay.WxMpPayRefundResult;
import me.chanjar.weixin.mp.bean.pay.WxMpPayResult;
import me.chanjar.weixin.mp.bean.pay.WxRedpackResult;
import me.chanjar.weixin.mp.bean.pay.WxSendRedpackRequest;
import me.chanjar.weixin.mp.bean.pay.WxUnifiedOrderRequest;
import me.chanjar.weixin.mp.bean.pay.WxUnifiedOrderResult;

/**
 * Created by Binary Wang on 2016/7/28.
 *
 * @author binarywang (https://github.com/binarywang)
 */
public class WxMpPayServiceImpl implements WxMpPayService {

  private static final String PAY_BASE_URL = "https://api.mch.weixin.qq.com";
  private static final List<String> TRADE_TYPES = Lists.newArrayList("JSAPI",
      "NATIVE", "APP");
  private WxMpService wxMpService;

  public WxMpPayServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxMpPayResult getJSSDKPayResult(String transactionId,
      String outTradeNo) throws WxErrorException {
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

    String url = PAY_BASE_URL + "/pay/orderquery";
    String responseContent = this.wxMpService.post(url, request.toString());
    XStream xstream = XStreamInitializer.getInstance();
    xstream.alias("xml", WxMpPayResult.class);
    return (WxMpPayResult) xstream.fromXML(responseContent);
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

    String url = PAY_BASE_URL + "/secapi/pay/refund";
    String responseContent = this.wxMpService.post(url, request.toString());
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
          + ";result_code:" + wxMpPayRefundResult.getResultCode() + ";err_code"
          + wxMpPayRefundResult.getErrCode() + ";err_code_des"
          + wxMpPayRefundResult.getErrCodeDes());
      throw new WxErrorException(error);
    }

    return wxMpPayRefundResult;
  }

  @Override
  public boolean checkJSSDKCallbackDataSignature(Map<String, String> kvm,
      String signature) {
    return signature.equals(this.createSign(kvm,
        this.wxMpService.getWxMpConfigStorage().getPartnerKey()));
  }

  @Override
  public WxRedpackResult sendRedpack(WxSendRedpackRequest request)
      throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxSendRedpackRequest.class);
    xstream.processAnnotations(WxRedpackResult.class);

    request.setWxAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(xmlBean2Map(request),
        this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/sendredpack";
    if (request.getAmtType() != null) {
      //裂变红包
      url = PAY_BASE_URL + "/mmpaymkttransfers/sendgroupredpack";
    }

    String responseContent = this.wxMpService.post(url, xstream.toXML(request));
    WxRedpackResult redpackResult = (WxRedpackResult) xstream
        .fromXML(responseContent);
    if ("FAIL".equals(redpackResult.getResultCode())) {
      throw new WxErrorException(WxError.newBuilder()
          .setErrorMsg(
              redpackResult.getErrCode() + ":" + redpackResult.getErrCodeDes())
          .build());
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
        Field field = bean.getClass().getDeclaredField(entry.getKey());
        if (field.isAnnotationPresent(XStreamAlias.class)) {
          result.put(field.getAnnotation(XStreamAlias.class).value(),
              reflect.get().toString());
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
      if (null != value && !"".equals(value) && !"sign".equals(key)
          && !"key".equals(key)) {
        toSign.append(key + "=" + value + "&");
      }
    }

    toSign.append("key=" + signKey);

    return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
  }

  @Override
  public WxUnifiedOrderResult unifiedOrder(WxUnifiedOrderRequest request)
      throws WxErrorException {
    checkParameters(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxUnifiedOrderRequest.class);
    xstream.processAnnotations(WxUnifiedOrderResult.class);

    request.setAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(xmlBean2Map(request),
        this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/unifiedorder";

    String responseContent = this.wxMpService.post(url, xstream.toXML(request));
    WxUnifiedOrderResult result = (WxUnifiedOrderResult) xstream
        .fromXML(responseContent);
    if ("FAIL".equals(result.getResultCode())) {
      throw new WxErrorException(WxError.newBuilder()
          .setErrorMsg(result.getErrCode() + ":" + result.getErrCodeDes())
          .build());
    }

    return result;

  }

  private void checkParameters(WxUnifiedOrderRequest request) {

    checkNotNullParams(request);

    if (!TRADE_TYPES.contains(request.getTradeType())) {
      throw new IllegalArgumentException("trade_type目前必须为" + TRADE_TYPES + "其中之一");

    }

    if ("JSAPI".equals(request.getTradeType()) && request.getOpenid() == null) {
      throw new IllegalArgumentException("当 trade_type是'JSAPI'时未指定openid");
    }

    if ("NATIVE".equals(request.getTradeType()) && request.getProductId() == null) {
      throw new IllegalArgumentException("当 trade_type是'NATIVE'时未指定product_id");
    }
  }

  private void checkNotNullParams(Object request) {
    List<String> nullFields = Lists.newArrayList();
    for (Entry<String, Reflect> entry : Reflect.on(request).fields()
        .entrySet()) {
      Reflect reflect = entry.getValue();
      try {
        Field field = request.getClass().getDeclaredField(entry.getKey());
        if (field.isAnnotationPresent(Required.class)
            && reflect.get() == null) {
          nullFields.add(entry.getKey());
        }
      } catch (NoSuchFieldException | SecurityException e) {
        e.printStackTrace();
      }
    }

    if (!nullFields.isEmpty()) {
      throw new IllegalArgumentException("必填字段[" + nullFields + "]必须提供值");
    }
  }

  @Override
  public Map<String, String> getPayInfo(WxUnifiedOrderRequest request) throws WxErrorException {
    WxUnifiedOrderResult unifiedOrderResult = this.unifiedOrder(request);

    if (!"SUCCESS".equalsIgnoreCase(unifiedOrderResult.getReturnCode())
        || !"SUCCESS".equalsIgnoreCase(unifiedOrderResult.getResultCode())) {
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1)
          .setErrorMsg("return_code:" + unifiedOrderResult.getReturnCode() + ";return_msg:"
          + unifiedOrderResult.getReturnMsg() + ";result_code:" + unifiedOrderResult.getResultCode() + ";err_code"
              + unifiedOrderResult.getErrCode() + ";err_code_des" + unifiedOrderResult.getErrCodeDes())
          .build());
    }

    String prepayId = unifiedOrderResult.getPrepayId();
    if (StringUtils.isBlank(prepayId)) {
      throw new RuntimeException(String.format("Failed to get prepay id due to error code '%s'(%s).",
          unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()));
    }

    Map<String, String> payInfo = new HashMap<>();
    payInfo.put("appId", this.wxMpService.getWxMpConfigStorage().getAppId());
    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
    payInfo.put("nonceStr", System.currentTimeMillis() + "");
    payInfo.put("package", "prepay_id=" + prepayId);
    payInfo.put("signType", "MD5");
    if ("NATIVE".equals(request.getTradeType())) {
      payInfo.put("codeUrl", unifiedOrderResult.getCodeURL());
    }

    String finalSign = this.createSign(payInfo, this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    payInfo.put("paySign", finalSign);
    return payInfo;
  }

  @Override
  public WxEntPayResult entPay(WxEntPayRequest request, File keyFile) throws WxErrorException {
    checkNotNullParams(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxEntPayRequest.class);
    xstream.processAnnotations(WxEntPayResult.class);

    request.setMchAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(xmlBean2Map(request), this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/promotion/transfers";

    try (FileInputStream instream = new FileInputStream(keyFile)) {
      String mchId = request.getMchId();
      KeyStore keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(instream, mchId.toCharArray());

      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
          new DefaultHostnameVerifier());

      try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(new String(xstream.toXML(request).getBytes("UTF-8"), "ISO-8859-1")));

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
          String responseContent = EntityUtils.toString(response.getEntity());
          WxEntPayResult result = (WxEntPayResult) xstream.fromXML(responseContent);
          if ("FAIL".equals(result.getResultCode())) {
            throw new WxErrorException(
                WxError.newBuilder().setErrorMsg(result.getErrCode() + ":" + result.getErrCodeDes()).build());
          }

          return result;
        }
      }
    } catch (Exception e) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg(e.getMessage()).build(), e);
    }
  }

}
