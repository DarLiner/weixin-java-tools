package me.chanjar.weixin.mp.bean.result;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * @author miller
 */
public class WxMediaImgUploadResult implements Serializable {
  private String url;

  public static WxMediaImgUploadResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMediaImgUploadResult.class);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
