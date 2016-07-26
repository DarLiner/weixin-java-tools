package me.chanjar.weixin.mp.bean.kefu.result;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.annotations.SerializedName;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
/**
 * 
 * @author Binary Wang
 *
 */
public class WxMpKfOnlineList {
  @SerializedName("kf_online_list")
  private List<WxMpKfInfo> kfOnlineList;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
  
  public List<WxMpKfInfo> getKfOnlineList() {
    return this.kfOnlineList;
  }

  public void setKfOnlineList(List<WxMpKfInfo> kfOnlineList) {
    this.kfOnlineList = kfOnlineList;
  }

  public static WxMpKfOnlineList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfOnlineList.class);
  }
}
