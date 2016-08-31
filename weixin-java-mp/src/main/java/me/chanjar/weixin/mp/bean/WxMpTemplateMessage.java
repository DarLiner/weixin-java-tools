package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WxMpTemplateMessage implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 5063374783759519418L;
  private String toUser;
  private String templateId;
  private String url;
  private String topColor;
  private List<WxMpTemplateData> data = new ArrayList<>();

  public String getToUser() {
    return this.toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getTemplateId() {
    return this.templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTopColor() {
    return this.topColor;
  }

  public void setTopColor(String topColor) {
    this.topColor = topColor;
  }

  public List<WxMpTemplateData> getData() {
    return this.data;
  }

  public void setData(List<WxMpTemplateData> data) {
    this.data = data;
  }

  public void addWxMpTemplateData(WxMpTemplateData datum) {
    this.data.add(datum);
  }

  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
}
