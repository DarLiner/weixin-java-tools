package me.chanjar.weixin.common.bean.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WxMenuButton {

  private String type;
  private String name;
  private String key;
  private String url;
  private String mediaId;

  private List<WxMenuButton> subButtons = new ArrayList<>();

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, 
        ToStringStyle.JSON_STYLE);
  }
  
  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<WxMenuButton> getSubButtons() {
    return this.subButtons;
  }

  public void setSubButtons(List<WxMenuButton> subButtons) {
    this.subButtons = subButtons;
  }

  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
}