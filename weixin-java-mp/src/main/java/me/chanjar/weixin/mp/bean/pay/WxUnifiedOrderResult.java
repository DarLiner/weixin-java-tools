package me.chanjar.weixin.mp.bean.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"返回的结果
 * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
 * </pre>
 *
 * @author chanjarster
 */
@XStreamAlias("xml")
public class WxUnifiedOrderResult {

  @XStreamAlias("return_code")
  private String returnCode;

  @XStreamAlias("return_msg")
  private String returnMsg;

  @XStreamAlias("appid")
  private String appid;

  @XStreamAlias("mch_id")
  private String mchId;

  @XStreamAlias("nonce_str")
  private String nonceStr;

  @XStreamAlias("sign")
  private String sign;

  @XStreamAlias("result_code")
  private String resultCode;

  @XStreamAlias("prepay_id")
  private String prepayId;

  @XStreamAlias("trade_type")
  private String tradeType;

  @XStreamAlias("err_code")
  private String errCode;

  @XStreamAlias("err_code_des")
  private String errCodeDes;

  @XStreamAlias("code_url")
  private String codeURL;

  public String getReturnCode() {
    return this.returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMsg() {
    return this.returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public String getAppid() {
    return this.appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getMchId() {
    return this.mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getNonceStr() {
    return this.nonceStr;
  }

  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }

  public String getSign() {
    return this.sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getResultCode() {
    return this.resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getPrepayId() {
    return this.prepayId;
  }

  public void setPrepayId(String prepayId) {
    this.prepayId = prepayId;
  }

  public String getTradeType() {
    return this.tradeType;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public String getErrCode() {
    return this.errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrCodeDes() {
    return this.errCodeDes;
  }

  public void setErrCodeDes(String errCodeDes) {
    this.errCodeDes = errCodeDes;
  }

  public String getCodeURL() {
    return this.codeURL;
  }

  public void setCodeURL(String codeURL) {
    this.codeURL = codeURL;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
