package me.chanjar.weixin.mp.bean.device;

/**
 * Created by keungtung on 10/12/2016.
 */
public class BaseResp extends AbstractDeviceBean{
  private BaseInfo base_info;
  private Integer errcode;
  private String errmsg;

  public Integer getErrcode() {
    return errcode;
  }

  public void setErrcode(Integer errcode) {
    this.errcode = errcode;
  }

  public BaseInfo getBase_info() {
    return base_info;
  }

  public void setBase_info(BaseInfo base_info) {
    this.base_info = base_info;
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
