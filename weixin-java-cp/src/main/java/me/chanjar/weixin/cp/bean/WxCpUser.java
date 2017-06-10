package me.chanjar.weixin.cp.bean;

import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信用户信息
 *
 * @author Daniel Qian
 */
public class WxCpUser implements Serializable {

  private static final long serialVersionUID = -5696099236344075582L;
  private String userId;
  private String name;
  private Integer[] departIds;
  private String position;
  private String mobile;
  private String gender;
  private String email;
  private String avatar;
  private Integer status;
  private Integer enable;
  private Integer isLeader;
  private final List<Attr> extAttrs = new ArrayList<>();
  private Integer hideMobile;
  private String englishName;
  private String telephone;

  public static WxCpUser fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpUser.class);
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer[] getDepartIds() {
    return this.departIds;
  }

  public void setDepartIds(Integer[] departIds) {
    this.departIds = departIds;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPosition() {
    return this.position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getTelephone() {
    return this.telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatar() {
    return this.avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getEnable() {
    return this.enable;
  }

  public void setEnable(Integer enable) {
    this.enable = enable;
  }

  public void addExtAttr(String name, String value) {
    this.extAttrs.add(new Attr(name, value));
  }

  public List<Attr> getExtAttrs() {
    return this.extAttrs;
  }

  public Integer getIsLeader() {
    return isLeader;
  }

  public void setIsLeader(Integer isLeader) {
    this.isLeader = isLeader;
  }

  public Integer getHideMobile() {
    return hideMobile;
  }

  public void setHideMobile(Integer hideMobile) {
    this.hideMobile = hideMobile;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public String toJson() {
    return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public static class Attr {

    private String name;
    private String value;

    public Attr(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getValue() {
      return this.value;
    }

    public void setValue(String value) {
      this.value = value;
    }

  }

}
