package com.github.binarywang.wxpay.bean.order;

/**
 * <pre>
 * 微信公众号支付进行统一下单后组装所需参数的类
 * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6
 * Created by Binary Wang on 2017-9-1.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxPayMpOrderResult {
  private String appId;
  private String timeStamp;
  private String nonceStr;
  /**
   * 由于package为java保留关键字，因此改为packageValue
   */
  private String packageValue;
  private String signType;
  private String paySign;

  private WxPayMpOrderResult(Builder builder) {
    setAppId(builder.appId);
    setTimeStamp(builder.timeStamp);
    setNonceStr(builder.nonceStr);
    setPackageValue(builder.packageValue);
    setSignType(builder.signType);
    setPaySign(builder.paySign);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getNonceStr() {
    return this.nonceStr;
  }

  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }

  public String getPackageValue() {
    return this.packageValue;
  }

  public void setPackageValue(String packageValue) {
    this.packageValue = packageValue;
  }

  public String getSignType() {
    return this.signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getPaySign() {
    return this.paySign;
  }

  public void setPaySign(String paySign) {
    this.paySign = paySign;
  }

  public WxPayMpOrderResult() {
  }


  public static final class Builder {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packageValue;
    private String signType;
    private String paySign;

    private Builder() {
    }

    public Builder appId(String appId) {
      this.appId = appId;
      return this;
    }

    public Builder timeStamp(String timeStamp) {
      this.timeStamp = timeStamp;
      return this;
    }

    public Builder nonceStr(String nonceStr) {
      this.nonceStr = nonceStr;
      return this;
    }

    public Builder packageValue(String packageValue) {
      this.packageValue = packageValue;
      return this;
    }

    public Builder signType(String signType) {
      this.signType = signType;
      return this;
    }

    public Builder paySign(String paySign) {
      this.paySign = paySign;
      return this;
    }

    public WxPayMpOrderResult build() {
      return new WxPayMpOrderResult(this);
    }
  }
}
