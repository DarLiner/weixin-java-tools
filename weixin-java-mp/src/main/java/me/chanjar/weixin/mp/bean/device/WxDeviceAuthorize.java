package me.chanjar.weixin.mp.bean.device;

import java.util.List;

/**
 * Created by keungtung on 10/12/2016.
 */
public class WxDeviceAuthorize extends  AbstractDeviceBean{
  private String deivce_num;
  private String op_type;
  private List<WxDevice> device_list;

  public String getDeivce_num() {
    return deivce_num;
  }

  public void setDeivce_num(String deivce_num) {
    this.deivce_num = deivce_num;
  }

  public String getOp_type() {
    return op_type;
  }

  public void setOp_type(String op_type) {
    this.op_type = op_type;
  }

  public List<WxDevice> getDevice_list() {
    return device_list;
  }

  public void setDevice_list(List<WxDevice> device_list) {
    this.device_list = device_list;
  }
}
