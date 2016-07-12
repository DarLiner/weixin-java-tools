package me.chanjar.weixin.mp.bean.kefu.result;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 客服基本信息以及客服在线状态信息
 * @author Binary Wang
 *
 */
public class WxMpKfInfo implements Serializable {
  private static final long serialVersionUID = -5877300750666022290L;

  /**
   * kf_account 完整客服账号，格式为：账号前缀@公众号微信号
   */
  @SerializedName("kf_account")
  private String account;

  /**
   * kf_headimgurl 客服头像地址
   */
  @SerializedName("kf_headimgurl")
  private String headImgUrl;

  /**
   * kf_id 客服工号
   */
  @SerializedName("kf_id")
  private String id;

  /**
   * kf_nick  客服昵称
   */
  @SerializedName("kf_nick")
  private String nick;

  /**
   *  status 客服在线状态 1：pc在线，2：手机在线。若pc和手机同时在线则为 1+2=3
   */
  @SerializedName("status")
  private Integer status;

  /**
   * auto_accept 客服设置的最大自动接入数
   */
  @Expose
  @SerializedName("auto_accept")
  private Integer autoAccept;

  /**
   * accepted_case 客服当前正在接待的会话数
   * @return
   */
  @Expose
  @SerializedName("accepted_case")
  private Integer acceptedCase;

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getAutoAccept() {
    return this.autoAccept;
  }

  public void setAutoAccept(Integer autoAccept) {
    this.autoAccept = autoAccept;
  }

  public Integer getAcceptedCase() {
    return this.acceptedCase;
  }

  public void setAcceptedCase(Integer acceptedCase) {
    this.acceptedCase = acceptedCase;
  }

  public String getAccount() {
    return this.account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getHeadImgUrl() {
    return this.headImgUrl;
  }

  public void setHeadImgUrl(String headImgUrl) {
    this.headImgUrl = headImgUrl;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
