package me.chanjar.weixin.mp.bean.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 企业付款返回结果
 * Created by Binary Wang on 2016/10/02.
 * @author binarywang (https://github.com/binarywang)
 */
@XStreamAlias("xml")
public class WxEntPayResult {

  /**
   * 返回状态码
   */
  @XStreamAlias("return_code")
  private String returnCode;

  /**
   * 返回信息
   */
  @XStreamAlias("return_msg")
  private String returnMsg;

  //############以下字段在return_code为SUCCESS的时候有返回

  /**
   * 商户appid
   */
  @XStreamAlias("mch_appid")
  private String mchAppid;

  /**
   * 商户号
   */
  @XStreamAlias("mchid")
  private String mchId;

  /**
   * 设备号
   */
  @XStreamAlias("device_info")
  private String deviceInfo;

  /**
   * 随机字符串
   */
  @XStreamAlias("nonce_str")
  private String nonceStr;

  /**
   * 业务结果
   */
  @XStreamAlias("result_code")
  private String resultCode;
  /**
   * 错误代码
   */
  @XStreamAlias("err_code")
  private String errCode;

  /**
   * 错误代码描述
   */
  @XStreamAlias("err_code_des")
  private String errCodeDes;

  //############以下字段在return_code 和result_code都为SUCCESS的时候有返回##############
  /**
   * 商户订单号
   */
  @XStreamAlias("partner_trade_no")
  private String partnerTradeNo;

  /**
   * 微信订单号
   */
  @XStreamAlias("payment_no")
  private String paymentNo;

  /**
   * 微信支付成功时间
   */
  @XStreamAlias("payment_time")
  private String paymentTime;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMsg() {
    return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public String getMchAppid() {
    return mchAppid;
  }

  public void setMchAppid(String mchAppid) {
    this.mchAppid = mchAppid;
  }

  public String getMchId() {
    return mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getDeviceInfo() {
    return deviceInfo;
  }

  public void setDeviceInfo(String deviceInfo) {
    this.deviceInfo = deviceInfo;
  }

  public String getNonceStr() {
    return nonceStr;
  }

  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrCodeDes() {
    return errCodeDes;
  }

  public void setErrCodeDes(String errCodeDes) {
    this.errCodeDes = errCodeDes;
  }

  public String getPartnerTradeNo() {
    return partnerTradeNo;
  }

  public void setPartnerTradeNo(String partnerTradeNo) {
    this.partnerTradeNo = partnerTradeNo;
  }

  public String getPaymentNo() {
    return paymentNo;
  }

  public void setPaymentNo(String paymentNo) {
    this.paymentNo = paymentNo;
  }

  public String getPaymentTime() {
    return paymentTime;
  }

  public void setPaymentTime(String paymentTime) {
    this.paymentTime = paymentTime;
  }
}
