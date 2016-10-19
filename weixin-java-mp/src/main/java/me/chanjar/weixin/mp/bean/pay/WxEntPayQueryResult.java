package me.chanjar.weixin.mp.bean.pay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 企业付款查询返回结果
 * Created by Binary Wang on 2016/10/19.
 * @author binarywang (https://github.com/binarywang)
 */
@XStreamAlias("xml")
public class WxEntPayQueryResult {

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
   * 商户号
   */
  @XStreamAlias("mchid")
  private String mchId;

  /**
   * 付款单号
   */
  @XStreamAlias("detail_id")
  private String detailId;

  /**
   * 转账状态
   */
  @XStreamAlias("status")
  private String status;

  /**
   * 失败原因
   */
  @XStreamAlias("reason")
  private String reason;

  /**
   * 收款用户openid
   */
  @XStreamAlias("openid")
  private String openid;

  /**
   * 收款用户姓名
   */
  @XStreamAlias("transfer_name")
  private String transferName;

  /**
   * 付款金额
   */
  @XStreamAlias("payment_amount")
  private Integer paymentAmount;

  /**
   * 转账时间
   */
  @XStreamAlias("transfer_time")
  private String transferTime;

  /**
   * 付款描述
   */
  @XStreamAlias("desc")
  private String desc;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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

  public String getMchId() {
    return mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getDetailId() {
    return detailId;
  }

  public void setDetailId(String detailId) {
    this.detailId = detailId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getTransferName() {
    return transferName;
  }

  public void setTransferName(String transferName) {
    this.transferName = transferName;
  }

  public Integer getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(Integer paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public String getTransferTime() {
    return transferTime;
  }

  public void setTransferTime(String transferTime) {
    this.transferTime = transferTime;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
