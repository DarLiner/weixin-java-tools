package me.chanjar.weixin.mp.bean.tag;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.annotations.SerializedName;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 获取标签下粉丝列表的结果对象
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016-09-19.
 */
public class WxTagListUser {

  public static WxTagListUser fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json,WxTagListUser.class);
  }

  public String toJson() {
    return WxMpGsonBuilder.create().toJson(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  /**
   *"count":2,这次获取的粉丝数量
   */
  @SerializedName("count")
  private Integer count;

  /**
   *"data" 粉丝列表
   */
  @SerializedName("data")
  private WxTagListUserData data;

  /**
   *"next_openid" 拉取列表最后一个用户的openid
   */
  @SerializedName("next_openid")
  private String nextOpenid;

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public WxTagListUserData getData() {
    return this.data;
  }

  public void setData(WxTagListUserData data) {
    this.data = data;
  }

  public String getNextOpenid() {
    return this.nextOpenid;
  }

  public void setNextOpenid(String nextOpenid) {
    this.nextOpenid = nextOpenid;
  }

  public static class WxTagListUserData {
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    /**
     * openid 列表
     */
    @SerializedName("openid")
    private List<String> openidList;

    public List<String> getOpenidList() {
      return this.openidList;
    }

    public void setOpenidList(List<String> openidList) {
      this.openidList = openidList;
    }
  }
}
