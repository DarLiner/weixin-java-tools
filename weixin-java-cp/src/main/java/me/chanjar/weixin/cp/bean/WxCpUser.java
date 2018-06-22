package me.chanjar.weixin.cp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信用户信息.
 *
 * @author Daniel Qian
 */
@Data
public class WxCpUser implements Serializable {
  private static final long serialVersionUID = -5696099236344075582L;
  private String userId;
  private String name;
  private Integer[] departIds;
  private String position;
  private String mobile;
  private Gender gender;
  private String email;
  private String avatar;
  private Integer status;
  private Integer enable;
  private Integer isLeader;
  private final List<Attr> extAttrs = new ArrayList<>();
  private Integer hideMobile;
  private String englishName;
  private String telephone;
  private String qrCode;
  private Boolean toInvite;

  public void addExtAttr(String name, String value) {
    this.extAttrs.add(new Attr(name, value));
  }

  public static WxCpUser fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpUser.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }

  @Data
  @AllArgsConstructor
  public static class Attr {
    private String name;
    private String value;
  }

}
