package me.chanjar.weixin.mp.bean.device;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;

/**
 * Created by keungtung on 14/12/2016.
 */
public class TransMsgResp extends AbstractDeviceBean{
  private Integer ret;
  private String ret_info;
  private Integer errcode;
  private String errmsg;

  public static TransMsgResp fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, TransMsgResp.class);
  }

  public Integer getRet() {
    return ret;
  }

  public void setRet(Integer ret) {
    this.ret = ret;
  }

  public String getRet_info() {
    return ret_info;
  }

  public void setRet_info(String ret_info) {
    this.ret_info = ret_info;
  }

  public Integer getErrcode() {
    return errcode;
  }

  public void setErrcode(Integer errcode) {
    this.errcode = errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }
}
