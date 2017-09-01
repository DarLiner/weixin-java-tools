package com.github.binarywang.wxpay.bean.order;

/**
 * <pre>
 * APP支付调用统一下单接口后的组装所需参数的实现类
 * 参考 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
 * Created by Binary Wang on 2017-9-1.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxPayAppOrderResult {
  private String sign;
  private String prepayId;
  private String partnerId;
  private String appId;
  private String packageValue;
  private String timeStamp;
  private String nonceStr;

  public WxPayAppOrderResult() {
  }

  private WxPayAppOrderResult(Builder builder) {
    setSign(builder.sign);
    setPrepayId(builder.prepayId);
    setPartnerId(builder.partnerId);
    setAppId(builder.appId);
    setPackageValue(builder.packageValue);
    setTimeStamp(builder.timeStamp);
    setNonceStr(builder.nonceStr);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getSign() {
    return this.sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getPrepayId() {
    return this.prepayId;
  }

  public void setPrepayId(String prepayId) {
    this.prepayId = prepayId;
  }

  public String getPartnerId() {
    return this.partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getPackageValue() {
    return this.packageValue;
  }

  public void setPackageValue(String packageValue) {
    this.packageValue = packageValue;
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

  public static final class Builder {
    private String sign;
    private String prepayId;
    private String partnerId;
    private String appId;
    private String packageValue;
    private String timeStamp;
    private String nonceStr;

    private Builder() {
    }

    public Builder sign(String sign) {
      this.sign = sign;
      return this;
    }

    public Builder prepayId(String prepayId) {
      this.prepayId = prepayId;
      return this;
    }

    public Builder partnerId(String partnerId) {
      this.partnerId = partnerId;
      return this;
    }

    public Builder appId(String appId) {
      this.appId = appId;
      return this;
    }

    public Builder packageValue(String packageValue) {
      this.packageValue = packageValue;
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

    public WxPayAppOrderResult build() {
      return new WxPayAppOrderResult(this);
    }
  }
}
