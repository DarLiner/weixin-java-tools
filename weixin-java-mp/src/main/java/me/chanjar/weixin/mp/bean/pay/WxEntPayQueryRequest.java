package me.chanjar.weixin.mp.bean.pay;

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
public class WxEntPayQueryRequest {
  /**
   * <pre>
   * Appid
   * appid
   * 是
   * wxe062425f740d30d8
   * String(32)
   * 商户号的appid
   * </pre>
  */
  @XStreamAlias("appid")
  private String appid;

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
   * 随机字符串
   * nonce_str
   * 是
   * 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
   * String(32)
   * 随机字符串，不长于32位
   * </pre>
  */
  @XStreamAlias("nonce_str")
  private String nonceStr;

  /**
   * <pre>
  * 签名
  * sign
  * 是
  * C380BEC2BFD727A4B6845133519F3AD6
  * String(32)
  *签名，详见签名算法
   * </pre>
  */
  @XStreamAlias("sign")
  private String sign;

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
