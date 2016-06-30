package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.ApacheHttpClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 * @author chanjarster
 *
 */
public class WxMpInMemoryConfigStorage implements WxMpConfigStorage {

  protected volatile String appId;
  protected volatile String secret;
  protected volatile String partnerId;
  protected volatile String partnerKey;
  protected volatile String token;
  protected volatile String accessToken;
  protected volatile String aesKey;
  protected volatile long expiresTime;

  protected volatile String oauth2redirectUri;

  protected volatile String http_proxy_host;
  protected volatile int http_proxy_port;
  protected volatile String http_proxy_username;
  protected volatile String http_proxy_password;

  protected volatile String jsapiTicket;
  protected volatile long jsapiTicketExpiresTime;

  protected volatile String cardApiTicket;
  protected volatile long cardApiTicketExpiresTime;

  /**
   * 临时文件目录
   */
  protected volatile File tmpDirFile;
  
  protected volatile SSLContext sslContext;

  protected volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

  @Override
  public String getAccessToken() {
    return this.accessToken;
  }

  @Override
  public boolean isAccessTokenExpired() {
    return System.currentTimeMillis() > this.expiresTime;
  }

  @Override
  public synchronized void updateAccessToken(WxAccessToken accessToken) {
    updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
  }
  
  @Override
  public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
    this.accessToken = accessToken;
    this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
  }

  @Override
  public void expireAccessToken() {
    this.expiresTime = 0;
  }

  @Override
  public String getJsapiTicket() {
    return this.jsapiTicket;
  }

  public void setJsapiTicket(String jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
  }

  public long getJsapiTicketExpiresTime() {
    return this.jsapiTicketExpiresTime;
  }

  public void setJsapiTicketExpiresTime(long jsapiTicketExpiresTime) {
    this.jsapiTicketExpiresTime = jsapiTicketExpiresTime;
  }

  @Override
  public boolean isJsapiTicketExpired() {
    return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
  }

  @Override
  public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
    this.jsapiTicket = jsapiTicket;
    // 预留200秒的时间
    this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
  }

  @Override
  public void expireJsapiTicket() {
    this.jsapiTicketExpiresTime = 0;
  }

  /**
   * 卡券api_ticket
   */
  @Override
  public String getCardApiTicket() {
    return this.cardApiTicket;
  }

  @Override
  public boolean isCardApiTicketExpired() {
    return System.currentTimeMillis() > this.cardApiTicketExpiresTime;
  }

  @Override
  public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
    this.cardApiTicket = cardApiTicket;
    // 预留200秒的时间
    this.cardApiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
  }

  @Override
  public void expireCardApiTicket() {
    this.cardApiTicketExpiresTime = 0;
  }

  @Override
  public String getAppId() {
    return this.appId;
  }

  @Override
  public String getSecret() {
    return this.secret;
  }

  @Override
  public String getToken() {
    return this.token;
  }

  @Override
  public long getExpiresTime() {
    return this.expiresTime;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String getAesKey() {
    return this.aesKey;
  }

  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setExpiresTime(long expiresTime) {
    this.expiresTime = expiresTime;
  }

  @Override
  public String getOauth2redirectUri() {
    return this.oauth2redirectUri;
  }

  public void setOauth2redirectUri(String oauth2redirectUri) {
    this.oauth2redirectUri = oauth2redirectUri;
  }

  @Override
  public String getHttp_proxy_host() {
    return this.http_proxy_host;
  }

  public void setHttp_proxy_host(String http_proxy_host) {
    this.http_proxy_host = http_proxy_host;
  }

  @Override
  public int getHttp_proxy_port() {
    return this.http_proxy_port;
  }

  public void setHttp_proxy_port(int http_proxy_port) {
    this.http_proxy_port = http_proxy_port;
  }

  @Override
  public String getHttp_proxy_username() {
    return this.http_proxy_username;
  }

  public void setHttp_proxy_username(String http_proxy_username) {
    this.http_proxy_username = http_proxy_username;
  }

  @Override
  public String getHttp_proxy_password() {
    return this.http_proxy_password;
  }

  public void setHttp_proxy_password(String http_proxy_password) {
    this.http_proxy_password = http_proxy_password;
  }

  @Override
  public String toString() {
    return "WxMpInMemoryConfigStorage{" +
        "appId='" + this.appId + '\'' +
        ", secret='" + this.secret + '\'' +
        ", token='" + this.token + '\'' +
        ", partnerId='" + this.partnerId + '\'' +
        ", partnerKey='" + this.partnerKey + '\'' +
        ", accessToken='" + this.accessToken + '\'' +
        ", aesKey='" + this.aesKey + '\'' +
        ", expiresTime=" + this.expiresTime +
        ", http_proxy_host='" + this.http_proxy_host + '\'' +
        ", http_proxy_port=" + this.http_proxy_port +
        ", http_proxy_username='" + this.http_proxy_username + '\'' +
        ", http_proxy_password='" + this.http_proxy_password + '\'' +
        ", jsapiTicket='" + this.jsapiTicket + '\'' +
        ", jsapiTicketExpiresTime='" + this.jsapiTicketExpiresTime + '\'' +
        ", cardApiTicket='" + this.cardApiTicket + '\'' +
        ", cardApiTicketExpiresTime='" + this.cardApiTicketExpiresTime + '\'' +
        ", tmpDirFile='" + this.tmpDirFile + '\'' +
        '}';
  }

  @Override
  public String getPartnerId() {
      return this.partnerId;
  }

  public void setPartnerId(String partnerId) {
      this.partnerId = partnerId;
  }

  @Override
  public String getPartnerKey() {
      return this.partnerKey;
  }

  public void setPartnerKey(String partnerKey) {
      this.partnerKey = partnerKey;
  }

  @Override
  public File getTmpDirFile() {
    return this.tmpDirFile;
  }

  public void setTmpDirFile(File tmpDirFile) {
    this.tmpDirFile = tmpDirFile;
  }

  @Override
  public SSLContext getSSLContext() {
    return this.sslContext;
  }
  
  public void setSSLContext(SSLContext context) {
    this.sslContext = context;
  }

  @Override
  public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
    return this.apacheHttpClientBuilder;
  }

  public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
    this.apacheHttpClientBuilder = apacheHttpClientBuilder;
  }
}
