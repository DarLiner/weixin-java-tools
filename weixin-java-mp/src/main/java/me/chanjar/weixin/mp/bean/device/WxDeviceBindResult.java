package me.chanjar.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * Created by keungtung on 10/12/2016.
 */
public class WxDeviceBindResult extends AbstractDeviceBean {
  private static final long serialVersionUID = 4687725146279339359L;

  @SerializedName("base_resp")
  private BaseResp baseResp;

  public static WxDeviceBindResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxDeviceBindResult.class);
  }

  public BaseResp getBaseResp() {
    return baseResp;
  }

  public void setBaseResp(BaseResp baseResp) {
    this.baseResp = baseResp;
  }
}
