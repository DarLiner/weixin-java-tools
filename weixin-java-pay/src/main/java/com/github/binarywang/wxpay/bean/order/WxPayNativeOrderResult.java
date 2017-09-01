package com.github.binarywang.wxpay.bean.order;

/**
 * <pre>
 * 微信扫码支付统一下单后发起支付拼接所需参数实现类
 * Created by Binary Wang on 2017-9-1.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxPayNativeOrderResult {
  private String codeUrl;

  private WxPayNativeOrderResult(Builder builder) {
    setCodeUrl(builder.codeUrl);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getCodeUrl() {
    return this.codeUrl;
  }

  public void setCodeUrl(String codeUrl) {
    this.codeUrl = codeUrl;
  }

  public WxPayNativeOrderResult() {
  }

  public static final class Builder {
    private String codeUrl;

    private Builder() {
    }

    public Builder codeUrl(String codeUrl) {
      this.codeUrl = codeUrl;
      return this;
    }

    public WxPayNativeOrderResult build() {
      return new WxPayNativeOrderResult(this);
    }
  }
}
