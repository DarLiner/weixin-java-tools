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
public class WxMpKfSessionList {
  /**
   * 会话列表
   */
  @SerializedName("sessionlist")
  private List<WxMpKfSession> kfSessionList;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  public static WxMpKfSessionList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json,
        WxMpKfSessionList.class);
  }

  public List<WxMpKfSession> getKfSessionList() {
    return this.kfSessionList;
  }

  public void setKfSessionList(List<WxMpKfSession> kfSessionList) {
    this.kfSessionList = kfSessionList;
  }
}
