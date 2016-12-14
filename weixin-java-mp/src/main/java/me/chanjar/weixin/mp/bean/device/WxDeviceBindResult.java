package me.chanjar.weixin.mp.bean.device;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * Created by keungtung on 10/12/2016.
 */
public class WxDeviceBindResult extends AbstractDeviceBean{
  private BaseResp base_resp;

  public static WxDeviceBindResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxDeviceBindResult.class);
  }

  public BaseResp getBase_resp() {
    return base_resp;
  }

  public void setBase_resp(BaseResp base_resp) {
    this.base_resp = base_resp;
  }
}
