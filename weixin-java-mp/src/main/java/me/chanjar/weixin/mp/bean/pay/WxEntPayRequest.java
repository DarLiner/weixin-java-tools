package me.chanjar.weixin.mp.bean.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 企业付款请求对象
 * 注释中各行含义如下：
 * 字段名、 变量名、 是否必填、 示例值、 类型、 描述
 * Created by Binary Wang on 2016/10/02.
 * @author binarywang (https://github.com/binarywang)
 */
@XStreamAlias("xml")
public class WxEntPayRequest {
  /**
  * 公众账号appid
  * mch_appid
  * 是
  * wx8888888888888888
  * String
  * 微信分配的公众账号ID（企业号corpid即为此appId）
  */
  @XStreamAlias("mch_appid")
  private String mchAppid;

  /**
  *  商户号
  *  mchid
  *  是
  *  1900000109
  *  String(32)
  *  微信支付分配的商户号
  */
  @XStreamAlias("mchid")
  private String mchId;

  /**
  * 设备号
  * device_info
  * 否
  * 13467007045764
  * String(32)
  *微信支付分配的终端设备号
  */
  @XStreamAlias("device_info")
  private String deviceInfo;

  /**
  * 随机字符串
  * nonce_str
  *是
  *5K8264ILTKCH16CQ2502SI8ZNMTM67VS
  *String(32)
  *随机字符串，不长于32位
  */
  @XStreamAlias("nonce_str")
  private String nonceStr;

  /**
  * 签名
  * sign
  * 是
  * C380BEC2BFD727A4B6845133519F3AD6
  * String(32)
  *签名，详见签名算法
  */
  @XStreamAlias("sign")
  private String sign;

  /**
  * 商户订单号
  * partner_trade_no
  * 是
  * 10000098201411111234567890
  * String
  * 商户订单号，
  */
  @XStreamAlias("partner_trade_no")
  private String partnerTradeNo;

  /**
  * 需保持唯一性 用户openid
  * openid
  * 是
  * oxTWIuGaIt6gTKsQRLau2M0yL16E
  * String
  * 商户appid下，某用户的openid
  */
  @XStreamAlias("openid")
  private String openid;

  /**
  * 校验用户姓名选项
  * check_name
  * 是
  * OPTION_CHECK
  * String
  * NO_CHECK：不校验真实姓名 
  * FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账） 
  * OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
  */
  @XStreamAlias("check_name")
  private String checkName;

  /**
  * 收款用户姓名
  * re_user_name
  * 可选
  * 马花花
  * String
  * 收款用户真实姓名。
  * 如果check_name设置为FORCE_CHECK或OPTION_CHECK，  则必填用户真实姓名
  */
  @XStreamAlias("re_user_name")
  private String reUserName;

  /**
  * 金额
  * amount
  * 是
  * 10099
  * int
  * 企业付款金额， 单位为分
  */
  @XStreamAlias("amount")
  private Integer amount;

  /**
  * 企业付款描述信息
  * desc
  * 是
  * 理赔
  * String
  * 企业付款操作说明信息。必填。
  */
  @XStreamAlias("desc")
  private String description;

  /**
  * Ip地址
  * spbill_create_ip
  * 是
  * 192.168.0.1
  * String(32)
  * 调用接口的机器Ip地址
  */
  @XStreamAlias("spbill_create_ip")
  private String spbillCreateIp;

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

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getPartnerTradeNo() {
    return partnerTradeNo;
  }

  public void setPartnerTradeNo(String partnerTradeNo) {
    this.partnerTradeNo = partnerTradeNo;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getCheckName() {
    return checkName;
  }

  public void setCheckName(String checkName) {
    this.checkName = checkName;
  }

  public String getReUserName() {
    return reUserName;
  }

  public void setReUserName(String reUserName) {
    this.reUserName = reUserName;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSpbillCreateIp() {
    return spbillCreateIp;
  }

  public void setSpbillCreateIp(String spbillCreateIp) {
    this.spbillCreateIp = spbillCreateIp;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
