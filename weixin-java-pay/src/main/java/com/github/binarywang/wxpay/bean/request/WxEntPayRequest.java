package com.github.binarywang.wxpay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import me.chanjar.weixin.common.annotation.Required;
import me.chanjar.weixin.common.util.ToStringUtils;

/**
 * <pre>
 * 企业付款请求对象
 * </pre>
 * Created by Binary Wang on 2016/10/02.
 *
 * @author binarywang (https://github.com/binarywang)
 */
@XStreamAlias("xml")
public class WxEntPayRequest extends WxPayBaseRequest {
  /**
   * <pre>
   * 字段名：公众账号appid
   * 变量名：mch_appid
   * 是否必填：是
   * 示例值：wx8888888888888888
   * 类型：String
   * 描述：微信分配的公众账号ID（企业号corpid即为此appId）
   * </pre>
   */
  @XStreamAlias("mch_appid")
  private String mchAppid;

  /**
   * <pre>
   * 字段名：商户号
   * 变量名：mchid
   * 是否必填：是
   * 示例值：1900000109
   * 类型：String(32)
   * 描述：微信支付分配的商户号
   * </pre>
   */
  @XStreamAlias("mchid")
  private String mchId;

  /**
   * <pre>
   * 字段名：设备号
   * 变量名：device_info
   * 是否必填：否
   * 示例值：13467007045764
   * 类型：String(32)
   * 描述：微信支付分配的终端设备号
   * </pre>
   */
  @XStreamAlias("device_info")
  private String deviceInfo;

  /**
   * <pre>
   * 字段名：商户订单号
   * 变量名：partner_trade_no
   * 是否必填：是
   * 示例值：10000098201411111234567890
   * 类型：String
   * 描述：商户订单号
   * </pre>
   */
  @Required
  @XStreamAlias("partner_trade_no")
  private String partnerTradeNo;

  /**
   * <pre>
   * 字段名：需保持唯一性 用户openid
   * 变量名：openid
   * 是否必填：是
   * 示例值：oxTWIuGaIt6gTKsQRLau2M0yL16E
   * 类型：String
   * 描述：商户appid下，某用户的openid
   * </pre>
   */
  @Required
  @XStreamAlias("openid")
  private String openid;

  /**
   * <pre>
   * 字段名：校验用户姓名选项
   * 变量名：check_name
   * 是否必填：是
   * 示例值：OPTION_CHECK
   * 类型：String
   * 描述：NO_CHECK：不校验真实姓名 
   * FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账） 
   * OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
   * </pre>
   */
  @Required
  @XStreamAlias("check_name")
  private String checkName;

  /**
   * <pre>
   * 字段名：收款用户姓名
   * 变量名：re_user_name
   * 是否必填：可选
   * 示例值：马花花
   * 类型：String
   * 描述：收款用户真实姓名。
   * 如果check_name设置为FORCE_CHECK或OPTION_CHECK，  则必填用户真实姓名
   * </pre>
   */
  @XStreamAlias("re_user_name")
  private String reUserName;

  /**
   * <pre>
   * 字段名：金额
   * 变量名：amount
   * 是否必填：是
   * 示例值：10099
   * 类型：int
   * 描述：企业付款金额， 单位为分
   * </pre>
   */
  @Required
  @XStreamAlias("amount")
  private Integer amount;

  /**
   * <pre>
   * 字段名：企业付款描述信息
   * 变量名：desc
   * 是否必填：是
   * 示例值：理赔
   * 类型：String
   * 描述：企业付款操作说明信息。必填。
   * </pre>
   */
  @Required
  @XStreamAlias("desc")
  private String description;

  /**
   * <pre>
   * 字段名：Ip地址
   * 变量名：spbill_create_ip
   * 是否必填：是
   * 示例值：192.168.0.1
   * 类型：String(32)
   * 描述：调用接口的机器Ip地址
   * </pre>
   */
  @Required
  @XStreamAlias("spbill_create_ip")
  private String spbillCreateIp;

  public WxEntPayRequest() {
  }

  private WxEntPayRequest(Builder builder) {
    setAppid(builder.appid);
    setMchId(builder.mchId);
    setSubAppId(builder.subAppId);
    setSubMchId(builder.subMchId);
    setNonceStr(builder.nonceStr);
    setSign(builder.sign);
    mchAppid = builder.mchAppid;
    setMchId(builder.mchId);
    setDeviceInfo(builder.deviceInfo);
    setPartnerTradeNo(builder.partnerTradeNo);
    setOpenid(builder.openid);
    setCheckName(builder.checkName);
    setReUserName(builder.reUserName);
    setAmount(builder.amount);
    setDescription(builder.description);
    setSpbillCreateIp(builder.spbillCreateIp);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  protected void checkConstraints() {

  }

  @Override
  public String getAppid() {
    return this.mchAppid;
  }

  @Override
  public void setAppid(String appid) {
    this.mchAppid = appid;
  }

  @Override
  public String getMchId() {
    return this.mchId;
  }

  @Override
  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getDeviceInfo() {
    return this.deviceInfo;
  }

  public void setDeviceInfo(String deviceInfo) {
    this.deviceInfo = deviceInfo;
  }

  public String getPartnerTradeNo() {
    return this.partnerTradeNo;
  }

  public void setPartnerTradeNo(String partnerTradeNo) {
    this.partnerTradeNo = partnerTradeNo;
  }

  public String getOpenid() {
    return this.openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getCheckName() {
    return this.checkName;
  }

  public void setCheckName(String checkName) {
    this.checkName = checkName;
  }

  public String getReUserName() {
    return this.reUserName;
  }

  public void setReUserName(String reUserName) {
    this.reUserName = reUserName;
  }

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSpbillCreateIp() {
    return this.spbillCreateIp;
  }

  public void setSpbillCreateIp(String spbillCreateIp) {
    this.spbillCreateIp = spbillCreateIp;
  }

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public static final class Builder {
    private String appid;
    private String mchId;
    private String deviceInfo;
    private String partnerTradeNo;
    private String openid;
    private String checkName;
    private String reUserName;
    private Integer amount;
    private String description;
    private String spbillCreateIp;
    private String subAppId;
    private String subMchId;
    private String nonceStr;
    private String sign;
    private String mchAppid;

    private Builder() {
    }

    public Builder appid(String appid) {
      this.appid = appid;
      return this;
    }

    public Builder mchId(String mchId) {
      this.mchId = mchId;
      return this;
    }

    public Builder deviceInfo(String deviceInfo) {
      this.deviceInfo = deviceInfo;
      return this;
    }

    public Builder partnerTradeNo(String partnerTradeNo) {
      this.partnerTradeNo = partnerTradeNo;
      return this;
    }

    public Builder openid(String openid) {
      this.openid = openid;
      return this;
    }

    public Builder checkName(String checkName) {
      this.checkName = checkName;
      return this;
    }

    public Builder reUserName(String reUserName) {
      this.reUserName = reUserName;
      return this;
    }

    public Builder amount(Integer amount) {
      this.amount = amount;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder spbillCreateIp(String spbillCreateIp) {
      this.spbillCreateIp = spbillCreateIp;
      return this;
    }

    public WxEntPayRequest build() {
      return new WxEntPayRequest(this);
    }

    public Builder subAppId(String subAppId) {
      this.subAppId = subAppId;
      return this;
    }

    public Builder subMchId(String subMchId) {
      this.subMchId = subMchId;
      return this;
    }

    public Builder nonceStr(String nonceStr) {
      this.nonceStr = nonceStr;
      return this;
    }

    public Builder sign(String sign) {
      this.sign = sign;
      return this;
    }

    public Builder mchAppid(String mchAppid) {
      this.mchAppid = mchAppid;
      return this;
    }
  }
}
