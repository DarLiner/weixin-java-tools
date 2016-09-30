package me.chanjar.weixin.mp.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WxXmlMpInMemoryConfigStorage
    extends WxMpInMemoryConfigStorage {

  private String openid;
  private String kfAccount;
  private String qrconnectRedirectUrl;
  private String templateId;

  public String getOpenid() {
    return this.openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public String getKfAccount() {
    return this.kfAccount;
  }

  public void setKfAccount(String kfAccount) {
    this.kfAccount = kfAccount;
  }

  public String getQrconnectRedirectUrl() {
    return this.qrconnectRedirectUrl;
  }

  public void setQrconnectRedirectUrl(String qrconnectRedirectUrl) {
    this.qrconnectRedirectUrl = qrconnectRedirectUrl;
  }

  public String getTemplateId() {
    return this.templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

}