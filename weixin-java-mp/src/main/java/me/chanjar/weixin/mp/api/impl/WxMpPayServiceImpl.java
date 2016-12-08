package me.chanjar.weixin.mp.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

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

import com.thoughtworks.xstream.XStream;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.BeanUtils;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpPayService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.pay.WxPayOrderNotifyResultConverter;
import me.chanjar.weixin.mp.bean.pay.request.WxEntPayQueryRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxEntPayRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayOrderCloseRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayOrderQueryRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayRedpackQueryRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayRefundQueryRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayRefundRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPaySendRedpackRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayUnifiedOrderRequest;
import me.chanjar.weixin.mp.bean.pay.result.WxEntPayQueryResult;
import me.chanjar.weixin.mp.bean.pay.result.WxEntPayResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayBaseResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayOrderCloseResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayOrderNotifyResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayOrderQueryResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayRedpackQueryResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayRefundQueryResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayRefundResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPaySendRedpackResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayUnifiedOrderResult;

/**
 * Created by Binary Wang on 2016/7/28.
 *
 * @author binarywang (https://github.com/binarywang)
 */
public class WxMpPayServiceImpl implements WxMpPayService {

  private static final String PAY_BASE_URL = "https://api.mch.weixin.qq.com";
  private static final String[] TRADE_TYPES = new String[]{"JSAPI", "NATIVE", "APP"};
  private static final String[] REFUND_ACCOUNT = new String[]{"REFUND_SOURCE_RECHARGE_FUNDS",
    "REFUND_SOURCE_UNSETTLED_FUNDS"};
  protected final Logger log = LoggerFactory.getLogger(this.getClass());
  private WxMpService wxMpService;
  private WxMpConfigStorage config;

  public WxMpPayServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
    this.config = wxMpService.getWxMpConfigStorage();
  }

  @Override
  public WxPayRefundResult refund(WxPayRefundRequest request, File keyFile)
    throws WxErrorException {
    checkParameters(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayRefundRequest.class);
    xstream.processAnnotations(WxPayRefundResult.class);

    request.setAppid(this.config.getAppId());
    String partnerId = this.config.getPartnerId();
    request.setMchId(partnerId);
    request.setNonceStr(System.currentTimeMillis() + "");
    request.setOpUserId(partnerId);
    request.setSign(this.createSign(request));

    String url = PAY_BASE_URL + "/secapi/pay/refund";
    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), partnerId);
    WxPayRefundResult result = (WxPayRefundResult) xstream.fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  @Override
  public WxPayRefundQueryResult refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxErrorException {
    if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(outRefundNo) && StringUtils.isBlank(refundId)) ||
      (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo) && StringUtils.isNotBlank(outRefundNo) && StringUtils.isNotBlank(refundId))) {
      throw new IllegalArgumentException("transaction_id ， out_trade_no，out_refund_no， refund_id 必须四选一");
    }

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayRefundQueryRequest.class);
    xstream.processAnnotations(WxPayRefundQueryResult.class);

    WxPayRefundQueryRequest request = new WxPayRefundQueryRequest();
    request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
    request.setTransactionId(StringUtils.trimToNull(transactionId));
    request.setOutRefundNo(StringUtils.trimToNull(outRefundNo));
    request.setRefundId(StringUtils.trimToNull(refundId));

    request.setAppid(this.config.getAppId());
    request.setMchId(this.config.getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");
    request.setSign(this.createSign(request));

    String url = PAY_BASE_URL + "/pay/refundquery";

    String responseContent = this.executeRequest(url, xstream.toXML(request));
    WxPayRefundQueryResult result = (WxPayRefundQueryResult) xstream.fromXML(responseContent);
    result.composeRefundRecords(responseContent);
    this.checkResult(result);
    return result;
  }
  

  private void checkResult(WxPayBaseResult result) throws WxErrorException {
  	//校验返回结果签名
  	if(!checkSign(result.toMap())){
  		throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg("参数格式校验错误！").build());
  	}
  	
  	//校验结果是否成功
    if (!"SUCCESS".equalsIgnoreCase(result.getReturnCode())
      || !"SUCCESS".equalsIgnoreCase(result.getResultCode())) {
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1)
        .setErrorMsg("返回代码: " + result.getReturnCode() + ", 返回信息: "
          + result.getReturnMsg() + ", 结果代码: " + result.getResultCode() + ", 错误代码: "
          + result.getErrCode() + ", 错误详情: " + result.getErrCodeDes())
        .build());
    }
  }

  private void checkParameters(WxPayRefundRequest request) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    if (StringUtils.isNotBlank(request.getRefundAccount())) {
      if (!ArrayUtils.contains(REFUND_ACCOUNT, request.getRefundAccount())) {
        throw new IllegalArgumentException("refund_account目前必须为" + Arrays.toString(REFUND_ACCOUNT) + "其中之一");
      }
    }

    if (StringUtils.isBlank(request.getOutTradeNo()) && StringUtils.isBlank(request.getTransactionId())) {
      throw new IllegalArgumentException("transaction_id 和 out_trade_no 不能同时为空，必须提供一个");
    }
  }

  @Override
  public WxPayOrderNotifyResult getOrderNotifyResult(String xmlData) throws WxErrorException {
    try {
      XStream xstream = XStreamInitializer.getInstance();
      xstream.autodetectAnnotations(true);
  		xstream.registerConverter(new WxPayOrderNotifyResultConverter(xstream.getMapper(),xstream.getReflectionProvider()));
  		WxPayOrderNotifyResult result = (WxPayOrderNotifyResult) xstream.fromXML(xmlData);
  		this.checkResult(result);
  		return result;
    }catch (WxErrorException e) {
    	log.error(e.getMessage(),e);
    	throw e;
		}catch (Exception e) {
    	log.error(e.getMessage(),e);
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("发生异常" + e.getMessage()).build());
    }
  }


  @Override
  public WxPaySendRedpackResult sendRedpack(WxPaySendRedpackRequest request, File keyFile)
    throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPaySendRedpackRequest.class);
    xstream.processAnnotations(WxPaySendRedpackResult.class);

    request.setWxAppid(this.config.getAppId());
    String mchId = this.config.getPartnerId();
    request.setMchId(mchId);
    request.setNonceStr(System.currentTimeMillis() + "");
    request.setSign(this.createSign(request));

    String url = PAY_BASE_URL + "/mmpaymkttransfers/sendredpack";
    if (request.getAmtType() != null) {
      //裂变红包
      url = PAY_BASE_URL + "/mmpaymkttransfers/sendgroupredpack";
    }

    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), mchId);
    WxPaySendRedpackResult result = (WxPaySendRedpackResult) xstream.fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  @Override
  public WxPayRedpackQueryResult queryRedpack(String mchBillNo, File keyFile) throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayRedpackQueryRequest.class);
    xstream.processAnnotations(WxPayRedpackQueryResult.class);

    WxPayRedpackQueryRequest request = new WxPayRedpackQueryRequest();
    request.setMchBillNo(mchBillNo);
    request.setBillType("MCHT");

    request.setAppid(this.config.getAppId());
    String mchId = this.config.getPartnerId();
    request.setMchId(mchId);
    request.setNonceStr(System.currentTimeMillis() + "");

    String sign = this.createSign(request);
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/gethbinfo";
    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), mchId);
    WxPayRedpackQueryResult result = (WxPayRedpackQueryResult) xstream.fromXML(responseContent);
    this.checkResult(result);
    return result;
  }
  
  @Override
  public String createSign(Object xmlBean) {
  	return createSign(BeanUtils.xmlBean2Map(xmlBean),this.config.getPartnerKey());
  }
  
  @Override
  public String createSign(Object xmlBean, String signKey) {
  	return createSign(BeanUtils.xmlBean2Map(xmlBean), signKey);
  }
  
  @Override
  public String createSign(Map<String, String> params) {
  	return createSign(params,this.config.getPartnerKey());
  }

  @Override
  public String createSign(Map<String, String> params, String signKey) {
    SortedMap<String, String> sortedMap = new TreeMap<>(params);

    StringBuilder toSign = new StringBuilder();
    for (String key : sortedMap.keySet()) {
      String value = params.get(key);
      if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
        toSign.append(key + "=" + value + "&");
      }
    }

    toSign.append("key=" + signKey);
    return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
  }
  
  @Override
  public boolean checkSign(Object xmlBean) {
    return checkSign(BeanUtils.xmlBean2Map(xmlBean) , this.config.getPartnerKey());
  }
  
  @Override
  public boolean checkSign(Object xmlBean, String signKey) {
    return checkSign(BeanUtils.xmlBean2Map(xmlBean) , signKey);
  }
  
  @Override
  public boolean checkSign(Map<String, String> params) {
    return checkSign(params , this.config.getPartnerKey());
  }
  
  @Override
  public boolean checkSign(Map<String, String> params, String signKey) {
  	String sign = this.createSign(params , signKey);
    return sign.equals(params.get("sign"));
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
    request.setAppid(this.config.getAppId());
    request.setMchId(this.config.getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");
    request.setSign(this.createSign(request));

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
    request.setAppid(this.config.getAppId());
    request.setMchId(this.config.getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");
    request.setSign(this.createSign(request));

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

    WxMpConfigStorage config =  this.config;
    
    //如果没有设置，则使用配置中默认值
    if(StringUtils.isBlank(request.getAppid())){
    	request.setAppid(config.getAppId());
    }
    if(StringUtils.isBlank(request.getMchId())){
    	 request.setMchId(config.getPartnerId());
    }
    if(StringUtils.isBlank(request.getNotifyURL())){
    	request.setNotifyURL(config.getNotifyURL());
    }
    if(StringUtils.isBlank(request.getTradeType())){
      request.setTradeType(config.getTradeType());
    }
    if(StringUtils.isBlank(request.getNonceStr())){
    	 request.setNonceStr(System.currentTimeMillis() + "");
    }
    request.setSign(this.createSign(request));

    String url = PAY_BASE_URL + "/pay/unifiedorder";

    String responseContent = this.executeRequest(url, xstream.toXML(request));
    WxPayUnifiedOrderResult result = (WxPayUnifiedOrderResult) xstream
      .fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  private void checkParameters(WxPayUnifiedOrderRequest request) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    if (!ArrayUtils.contains(TRADE_TYPES, request.getTradeType())) {
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
    payInfo.put("appId", this.config.getAppId());
    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
    payInfo.put("nonceStr", System.currentTimeMillis() + "");
    payInfo.put("package", "prepay_id=" + prepayId);
    payInfo.put("signType", "MD5");
    if ("NATIVE".equals(request.getTradeType())) {
      payInfo.put("codeUrl", unifiedOrderResult.getCodeURL());
    }
    payInfo.put("paySign", this.createSign(payInfo));
    return payInfo;
  }

  @Override
  public WxEntPayResult entPay(WxEntPayRequest request, File keyFile) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxEntPayRequest.class);
    xstream.processAnnotations(WxEntPayResult.class);

    request.setMchAppid(this.config.getAppId());
    request.setMchId(this.config.getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");
    request.setSign(this.createSign(request));

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
    request.setAppid(this.config.getAppId());
    request.setMchId(this.config.getPartnerId());
    request.setNonceStr(System.currentTimeMillis() + "");
    request.setSign(this.createSign(request));

    String url = PAY_BASE_URL + "/mmpaymkttransfers/gettransferinfo";

    String responseContent = this.executeRequestWithKeyFile(url, keyFile, xstream.toXML(request), request.getMchId());
    WxEntPayQueryResult result = (WxEntPayQueryResult) xstream.fromXML(responseContent);
    this.checkResult(result);
    return result;
  }

  private String executeRequest(String url, String requestStr) throws WxErrorException {
    HttpPost httpPost = new HttpPost(url);
    if (this.wxMpService.getHttpProxy() != null) {
      httpPost.setConfig(RequestConfig.custom().setProxy(this.wxMpService.getHttpProxy()).build());
    }

    try (CloseableHttpClient httpclient = HttpClients.custom().build()) {
      httpPost.setEntity(new StringEntity(new String(requestStr.getBytes("UTF-8"), "ISO-8859-1")));

      try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
        String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
        this.log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}", url, requestStr, result);
        return result;
      }
    } catch (IOException e) {
      this.log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", url, requestStr, e.getMessage());
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(e.getMessage()).build(), e);
    } finally {
      httpPost.releaseConnection();
    }
  }

  private String executeRequestWithKeyFile(String url, File keyFile, String requestStr, String mchId) throws WxErrorException {
    try (FileInputStream inputStream = new FileInputStream(keyFile)) {
      KeyStore keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(inputStream, mchId.toCharArray());

      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
        new DefaultHostnameVerifier());

      HttpPost httpPost = new HttpPost(url);
      if (this.wxMpService.getHttpProxy() != null) {
        httpPost.setConfig(RequestConfig.custom().setProxy(this.wxMpService.getHttpProxy()).build());
      }

      try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
        httpPost.setEntity(new StringEntity(new String(requestStr.getBytes("UTF-8"), "ISO-8859-1")));
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
          String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
          this.log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}", url, requestStr, result);
          return result;
        }
      } finally {
        httpPost.releaseConnection();
      }
    } catch (Exception e) {
      this.log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", url, requestStr, e.getMessage());
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(e.getMessage()).build(), e);
    }
  }
}
