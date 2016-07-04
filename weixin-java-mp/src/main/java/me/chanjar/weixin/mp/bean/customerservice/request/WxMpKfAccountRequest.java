package me.chanjar.weixin.mp.bean.customerservice.request;

import java.io.Serializable;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

public class WxMpKfAccountRequest implements Serializable {
  private static final long serialVersionUID = -5451863610674856927L;

  /**
   * kf_account   完整客服账号，格式为：账号前缀@公众号微信号
   */
  @SerializedName("kf_account")
  private String kfAccount;
  
  /**
   * nickname   客服昵称，最长6个汉字或12个英文字符
   */
  @SerializedName("nickname")
  private String nickName;
  
  /**
   * password   客服账号登录密码，格式为密码明文的32位加密MD5值
   */
  @SerializedName("password")
  private String password;
  
  /**
   * rawPassword   客服账号登录密码，明文密码，仅用于辅助操作
   */
  @SerializedName("rawPassword")
  private String rawPassword;
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public String getKfAccount() {
    return this.kfAccount;
  }

  public void setKfAccount(String kfAccount) {
    this.kfAccount = kfAccount;
  }

  public String getNickName() {
    return this.nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRawPassword() {
    return this.rawPassword;
  }

  public void setRawPassword(String rawPassword) {
    this.rawPassword = rawPassword;
  }

  public static Builder builder() {
      return new Builder();
  }

  public static class Builder {
      private String kfAccount;
      private String nickName;
      private String password;
      private String rawPassword;

      @SuppressWarnings("hiding")
      public Builder kfAccount(String kfAccount) {
          this.kfAccount = kfAccount;
          return this;
      }

      @SuppressWarnings("hiding")
      public Builder nickName(String nickName) {
          this.nickName = nickName;
          return this;
      }

      @SuppressWarnings("hiding")
      public Builder password(String password) {
          this.password = password;
          return this;
      }

      @SuppressWarnings("hiding")
      public Builder rawPassword(String rawPassword) {
          this.rawPassword = rawPassword;
          this.password(Md5Crypt.md5Crypt(rawPassword.getBytes()));
          return this;
      }

      public Builder from(WxMpKfAccountRequest origin) {
          this.kfAccount(origin.kfAccount);
          this.nickName(origin.nickName);
          this.password(origin.password);
          this.rawPassword(origin.rawPassword);
          return this;
      }

      public WxMpKfAccountRequest build() {
          WxMpKfAccountRequest m = new WxMpKfAccountRequest();
          m.kfAccount = this.kfAccount;
          m.nickName = this.nickName;
          m.password = this.password;
          m.rawPassword = this.rawPassword;
          return m;
      }
  }

}
