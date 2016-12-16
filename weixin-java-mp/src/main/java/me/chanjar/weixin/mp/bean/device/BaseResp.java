package me.chanjar.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;

/**
 * Created by keungtung on 10/12/2016.
 */
public class BaseResp extends AbstractDeviceBean{
  @SerializedName("base_info")
  private BaseInfo baseInfo;
  private Integer errcode;
  private String errmsg;

  public Integer getErrcode() {
    return errcode;
  }

  public void setErrcode(Integer errcode) {
    this.errcode = errcode;
  }

  public BaseInfo getBaseInfo() {
    return baseInfo;
  }

  public void setBaseInfo(BaseInfo baseInfo) {
    this.baseInfo = baseInfo;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  private class BaseInfo {
    private String device_type;
    private String device_id;

    public String getDevice_type() {
      return device_type;
    }

    public void setDevice_type(String device_type) {
      this.device_type = device_type;
    }

    public String getDevice_id() {
      return device_id;
    }

    public void setDevice_id(String device_id) {
      this.device_id = device_id;
    }
  }
}
