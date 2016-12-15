package me.chanjar.weixin.mp.bean.device;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * Created by keungtung on 10/12/2016.
 */
public class WxDeviceQrCodeResult extends AbstractDeviceBean{
  private String deviceid;
  private String qrticket;
  private String devcielicence;
  private RespMsg resp_msg;

  public static WxDeviceQrCodeResult fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxDeviceQrCodeResult.class);
  }

  public String getDevcielicence() {
    return devcielicence;
  }

  public void setDevcielicence(String devcielicence) {
    this.devcielicence = devcielicence;
  }

  public RespMsg getResp_msg() {
    return resp_msg;
  }

  public void setResp_msg(RespMsg resp_msg) {
    this.resp_msg = resp_msg;
  }

  public String getDeviceid() {
    return deviceid;
  }

  public void setDeviceid(String deviceid) {
    this.deviceid = deviceid;
  }

  public String getQrticket() {
    return qrticket;
  }

  public void setQrticket(String qrticket) {
    this.qrticket = qrticket;
  }
}
