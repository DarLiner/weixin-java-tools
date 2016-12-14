package me.chanjar.weixin.mp.bean.device;

import me.chanjar.weixin.common.util.ToStringUtils;

/**
 * Created by keungtung on 10/12/2016.
 */
public class WxDeviceMsg extends AbstractDeviceBean{
  private String deviceType;
  private String deviceId;
  private String openId;
  private String content;

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
