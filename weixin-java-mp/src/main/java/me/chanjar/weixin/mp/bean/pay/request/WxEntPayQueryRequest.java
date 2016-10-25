package me.chanjar.weixin.mp.bean.pay.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import me.chanjar.weixin.common.annotation.Required;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <pre>
 * 企业付款请求对象
 * 注释中各行每个字段描述对应如下：
 * <li>字段名
 * <li>变量名
 * <li>是否必填
 * <li>类型
 * <li>示例值
 * <li>描述
 * </pre>
 * Created by Binary Wang on 2016/10/19.
 * @author binarywang (https://github.com/binarywang)
 */
@XStreamAlias("xml")
public class WxEntPayQueryRequest extends WxPayBaseRequest {
  /**
   * <pre>
   * 商户号
   * mch_id
   * 是
   * 10000098
   * String(32)
   * 微信支付分配的商户号
   * </pre>
  */
  @XStreamAlias("mchid")
  private String mchId;

  /**
   * <pre>
  * 商户订单号
  * partner_trade_no
  * 是
  * 10000098201411111234567890
  * String
  * 商户订单号
   * </pre>
  */
  @Required
  @XStreamAlias("partner_trade_no")
  private String partnerTradeNo;

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getMchId() {
    return mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
