package me.chanjar.weixin.mp.bean.pay;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 向微信用户个人发现金红包返回结果
 * https://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5
 * @author kane
 *
 */
@XStreamAlias("xml")
public class WxRedpackResult implements Serializable {

  private static final long serialVersionUID = -4837415036337132073L;

  @XStreamAlias("return_code")
  private String returnCode;
  @XStreamAlias("return_msg")
  private String returnMsg;
  @XStreamAlias("sign")
  private String sign;
  @XStreamAlias("result_code")
  private String resultCode;
  
  @XStreamAlias("err_code")
  private String errCode;
  @XStreamAlias("err_code_des")
  private String errCodeDes;
  @XStreamAlias("mch_billno")
  private String mchBillno;
  @XStreamAlias("mch_id")
  private String mchId;
  @XStreamAlias("wxappid")
  private String wxappid;
  @XStreamAlias("re_openid")
  private String reOpenid;
  @XStreamAlias("total_amount")
  private int totalAmount;
  @XStreamAlias("send_time")
  private String sendTime;
  @XStreamAlias("send_listid")
  private String sendListid;
  
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
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
