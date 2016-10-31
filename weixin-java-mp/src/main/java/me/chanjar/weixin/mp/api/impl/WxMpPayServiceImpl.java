package me.chanjar.weixin.mp.api.impl;

import com.thoughtworks.xstream.XStream;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.BeanUtils;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpPayService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.pay.WxPayJsSDKCallback;
import me.chanjar.weixin.mp.bean.pay.result.WxPayOrderCloseResult;
import me.chanjar.weixin.mp.bean.pay.request.*;
import me.chanjar.weixin.mp.bean.pay.result.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.*;

/**
 * Created by Binary Wang on 2016/7/28.
 *
 * @author binarywang (https://github.com/binarywang)
 */
public class WxMpPayServiceImpl implements WxMpPayService {

  protected final Logger log = LoggerFactory.getLogger(this.getClass());

  private static final String PAY_BASE_URL = "https://api.mch.weixin.qq.com";
  private static final String[] TRADE_TYPES = new String[]{"JSAPI","NATIVE", "APP"};
  private static final String[] REFUND_ACCOUNT =  new String[]{"REFUND_SOURCE_RECHARGE_FUNDS",
    "REFUND_SOURCE_UNSETTLED_FUNDS"};

  private WxMpService wxMpService;

  public WxMpPayServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxPayRefundResult refund(WxPayRefundRequest request, File keyFile)
      throws WxErrorException {
    checkParameters(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayRefundRequest.class);
    xstream.processAnnotations(WxPayRefundResult.class);

    request.setAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    String partnerId = this.wxMpService.getWxMpConfigStorage().getPartnerId();
    request.setMchId(partnerId);
    request.setNonceStr( System.currentTimeMillis() + "");
    request.setOpUserId(partnerId);
    String sign = this.createSign(BeanUtils.xmlBean2Map(request), this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/secapi/pay/refund";
    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), partnerId);
    WxPayRefundResult result = (WxPayRefundResult) xstream.fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  private void checkResult(WxPayBaseResult result) throws WxErrorException {
    if (!"SUCCESS".equalsIgnoreCase(result.getReturnCode())
      || !"SUCCESS".equalsIgnoreCase(result.getResultCode())) {
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1)
        .setErrorMsg("返回代码:" + result.getReturnCode() + ", 返回信息: "
          + result.getReturnMsg() + ", 结果代码: " + result.getResultCode() + ", 错误代码: "
          + result.getErrCode() + ", 错误详情: " + result.getErrCodeDes())
        .build());
    }
  }

  private void checkParameters(WxPayRefundRequest request) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    if (StringUtils.isNotBlank(request.getRefundAccount())) {
      if(!ArrayUtils.contains(REFUND_ACCOUNT, request.getRefundAccount())){
        throw new IllegalArgumentException("refund_account目前必须为" + Arrays.toString(REFUND_ACCOUNT) + "其中之一");
      }
    }

    if (StringUtils.isBlank(request.getOutTradeNo()) && StringUtils.isBlank(request.getTransactionId())) {
      throw new IllegalArgumentException("transaction_id 和 out_trade_no 不能同时为空，必须提供一个");
    }
  }

  @Override
  public WxPayJsSDKCallback getJSSDKCallbackData(String xmlData) throws WxErrorException {
    try {
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxPayJsSDKCallback.class);
      return (WxPayJsSDKCallback) xstream.fromXML(xmlData);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("发生异常" + e.getMessage()).build());
    }
  }

  @Override
  public boolean checkJSSDKCallbackDataSignature(Map<String, String> kvm,
      String signature) {
    return signature.equals(this.createSign(kvm,
        this.wxMpService.getWxMpConfigStorage().getPartnerKey()));
  }

  @Override
  public WxPaySendRedpackResult sendRedpack(WxPaySendRedpackRequest request, File keyFile)
      throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPaySendRedpackRequest.class);
    xstream.processAnnotations(WxPaySendRedpackResult.class);

    request.setWxAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    String mchId = this.wxMpService.getWxMpConfigStorage().getPartnerId();
    request.setMchId(mchId);
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(BeanUtils.xmlBean2Map(request),
        this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/sendredpack";
    if (request.getAmtType() != null) {
      //裂变红包
      url = PAY_BASE_URL + "/mmpaymkttransfers/sendgroupredpack";
    }

    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), mchId);
    WxPaySendRedpackResult result = (WxPaySendRedpackResult) xstream
        .fromXML(responseContent);
    this.checkResult(result);
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
  public WxPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxErrorException {
    if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)) ||
      (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo))) {
      throw new IllegalArgumentException("transaction_id 和 out_trade_no 不能同时存在或同时为空，必须二选一");
    }

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayOrderQueryRequest.class);
    xstream.processAnnotations(WxPayOrderQueryResult.class);

    WxPayOrderQueryRequest request = new WxPayOrderQueryRequest();
    request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
    request.setTransactionId(StringUtils.trimToNull(transactionId));
    request.setAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(BeanUtils.xmlBean2Map(request),
      this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/orderquery";

    String responseContent = this.executeRequest(url, xstream.toXML(request));
    WxPayOrderQueryResult result = (WxPayOrderQueryResult) xstream.fromXML(responseContent);
    result.composeCoupons(responseContent);
    this.checkResult(result);
    return result;
  }

  @Override
  public WxPayOrderCloseResult closeOrder(String outTradeNo) throws WxErrorException {
    if (StringUtils.isBlank(outTradeNo)) {
      throw new IllegalArgumentException("out_trade_no 不能为空");
    }

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayOrderCloseRequest.class);
    xstream.processAnnotations(WxPayOrderCloseResult.class);

    WxPayOrderCloseRequest request = new WxPayOrderCloseRequest();
    request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
    request.setAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(BeanUtils.xmlBean2Map(request),
      this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/closeorder";

    String responseContent = this.executeRequest(url, xstream.toXML(request));
    WxPayOrderCloseResult result = (WxPayOrderCloseResult) xstream.fromXML(responseContent);
    this.checkResult(result);

    return result;
  }

  @Override
  public WxPayUnifiedOrderResult unifiedOrder(WxPayUnifiedOrderRequest request)
      throws WxErrorException {
    checkParameters(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayUnifiedOrderRequest.class);
    xstream.processAnnotations(WxPayUnifiedOrderResult.class);

    request.setAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(BeanUtils.xmlBean2Map(request),
        this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/unifiedorder";

    String responseContent = this.executeRequest(url, xstream.toXML(request));
    WxPayUnifiedOrderResult result = (WxPayUnifiedOrderResult) xstream
        .fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  private void checkParameters(WxPayUnifiedOrderRequest request) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    if (! ArrayUtils.contains(TRADE_TYPES, request.getTradeType())) {
      throw new IllegalArgumentException("trade_type目前必须为" + Arrays.toString(TRADE_TYPES) + "其中之一");
    }

    if ("JSAPI".equals(request.getTradeType()) && request.getOpenid() == null) {
      throw new IllegalArgumentException("当 trade_type是'JSAPI'时未指定openid");
    }

    if ("NATIVE".equals(request.getTradeType()) && request.getProductId() == null) {
      throw new IllegalArgumentException("当 trade_type是'NATIVE'时未指定product_id");
    }
  }

  @Override
  public Map<String, String> getPayInfo(WxPayUnifiedOrderRequest request) throws WxErrorException {
    WxPayUnifiedOrderResult unifiedOrderResult = this.unifiedOrder(request);
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
    BeanUtils.checkRequiredFields(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxEntPayRequest.class);
    xstream.processAnnotations(WxEntPayResult.class);

    request.setMchAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(BeanUtils.xmlBean2Map(request), this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/promotion/transfers";

    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), request.getMchId());
    WxEntPayResult result = (WxEntPayResult) xstream.fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  @Override
  public WxEntPayQueryResult queryEntPay(String partnerTradeNo, File keyFile) throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxEntPayQueryRequest.class);
    xstream.processAnnotations(WxEntPayQueryResult.class);

    WxEntPayQueryRequest request = new WxEntPayQueryRequest();
    request.setAppid(this.wxMpService.getWxMpConfigStorage().getAppId());
    request.setMchId(this.wxMpService.getWxMpConfigStorage().getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(BeanUtils.xmlBean2Map(request), this.wxMpService.getWxMpConfigStorage().getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/gettransferinfo";

    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), request.getMchId());
    WxEntPayQueryResult result = (WxEntPayQueryResult) xstream.fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  private String executeRequest( String url, String requestStr) throws WxErrorException {
    HttpPost httpPost = new HttpPost(url);
    if (this.wxMpService.getHttpProxy() != null) {
      httpPost.setConfig(RequestConfig.custom().setProxy(this.wxMpService.getHttpProxy()).build());
    }

    try (CloseableHttpClient httpclient = HttpClients.custom().build()) {
      httpPost.setEntity(new StringEntity(new String(requestStr.getBytes("UTF-8"), "ISO-8859-1")));

      try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
        String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
        this.log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}",url, requestStr, result);
        return result;
      }
    } catch (IOException e) {
      this.log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", url, requestStr, e.getMessage());
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(e.getMessage()).build(), e);
    }finally {
      httpPost.releaseConnection();
    }
  }

  private String executeRequestWithKeyFile( String url, File keyFile, String requestStr, String mchId) throws WxErrorException {
    try (FileInputStream inputStream = new FileInputStream(keyFile)) {
      KeyStore keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(inputStream, mchId.toCharArray());

      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
          new DefaultHostnameVerifier());

      HttpPost httpPost = new HttpPost(url);
      if (this.wxMpService.getHttpProxy() != null) {
        httpPost.setConfig(RequestConfig.custom().setProxy(this.wxMpService.getHttpProxy()).build());
      }

      try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
        httpPost.setEntity(new StringEntity(new String(requestStr.getBytes("UTF-8"), "ISO-8859-1")));
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
          String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
          this.log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}",url, requestStr, result);
          return result;
        }
      }finally {
        httpPost.releaseConnection();
      }
    } catch (Exception e) {
      this.log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", url, requestStr, e.getMessage());
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(e.getMessage()).build(), e);
    }
  }

}
