package com.github.binarywang.wxpay.bean.notify;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;

/**
 * 微信支付订单和退款的异步通知共用的响应类
 */
@XStreamAlias("xml")
public class WxPayNotifyResponse {
  @XStreamOmitField
  private transient static final String FAIL = "FAIL";
  @XStreamOmitField
  private transient static final String SUCCESS = "SUCCESS";

  @XStreamAlias("return_code")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String returnCode;
  @XStreamConverter(value = XStreamCDataConverter.class)
  @XStreamAlias("return_msg")
  private String returnMsg;

  public WxPayNotifyResponse() {
    super();
  }

  public WxPayNotifyResponse(String returnCode, String returnMsg) {
    super();
    this.returnCode = returnCode;
    this.returnMsg = returnMsg;
  }

  public static String fail(String msg) {
    WxPayNotifyResponse response = new WxPayNotifyResponse(FAIL, msg);
    XStream xstream = XStreamInitializer.getInstance();
    xstream.autodetectAnnotations(true);
    return xstream.toXML(response);
  }

  public static String success(String msg) {
    WxPayNotifyResponse response = new WxPayNotifyResponse(SUCCESS, msg);
    XStream xstream = XStreamInitializer.getInstance();
    xstream.autodetectAnnotations(true);
    return xstream.toXML(response);
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
}
