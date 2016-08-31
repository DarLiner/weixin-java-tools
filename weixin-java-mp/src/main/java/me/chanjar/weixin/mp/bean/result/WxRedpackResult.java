package me.chanjar.weixin.mp.bean.result;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 向微信用户个人发现金红包返回结果
 * https://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5
 * @author kane
 *
 */
@XStreamAlias("xml")
public class WxRedpackResult implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4837415036337132073L;

  @XStreamAlias("return_code")
  String returnCode;
  @XStreamAlias("return_msg")
  String returnMsg;
  @XStreamAlias("sign")
  String sign;
  @XStreamAlias("result_code")
  String resultCode;
  
  @XStreamAlias("err_code")
  String errCode;
  @XStreamAlias("err_code_des")
  String errCodeDes;
  @XStreamAlias("mch_billno")
  String mchBillno;
  @XStreamAlias("mch_id")
  String mchId;
  @XStreamAlias("wxappid")
  String wxappid;
  @XStreamAlias("re_openid")
  String reOpenid;
  @XStreamAlias("total_amount")
  int totalAmount;
  @XStreamAlias("send_time")
  String sendTime;
  @XStreamAlias("send_listid")
  String sendListid;
  
  public String getErrCode() {
    return this.errCode;
  }
  
  public String getErrCodeDes() {
    return this.errCodeDes;
  }

  public String getReturnCode() {
    return this.returnCode;
  }

  public String getReturnMsg() {
    return this.returnMsg;
  }

  public String getSign() {
    return this.sign;
  }

  public String getResultCode() {
    return this.resultCode;
  }

  public String getMchBillno() {
    return this.mchBillno;
  }

  public String getMchId() {
    return this.mchId;
  }

  public String getWxappid() {
    return this.wxappid;
  }

  public String getReOpenid() {
    return this.reOpenid;
  }

  public int getTotalAmount() {
    return this.totalAmount;
  }

  public String getSendTime() {
    return this.sendTime;
  }

  public String getSendListid() {
    return this.sendListid;
  }
  
  @Override
  public String toString() {
    return "WxRedpackResult{" +
        "returnCode=" + this.returnCode +
        ", returnMsg=" + this.returnMsg +
        ", sign=" + this.sign +
        ", errCode=" + this.errCode +
        ", errCodeDes=" + this.errCodeDes +
        ", mchBillno=" + this.mchBillno +
        ", mchId=" + this.mchId +
        ", wxappid=" + this.wxappid +
        ", reOpenid=" + this.reOpenid +
        ", totalAmount=" + this.totalAmount +
        ", sendTime=" + this.sendTime +
        ", sendListid=" + this.sendListid +
        '}';
  }
}
