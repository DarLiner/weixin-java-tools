package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.ApacheHttpClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;

/**
 * 微信客户端配置存储
 * @author chanjarster
 *
 */
public interface WxMpConfigStorage {

  String getAccessToken();

  boolean isAccessTokenExpired();

  /**
   * 强制将access token过期掉
   */
  void expireAccessToken();

  /**
   * 应该是线程安全的
   * @param accessToken
   */
  void updateAccessToken(WxAccessToken accessToken);

  /**
   * 应该是线程安全的
   * @param accessToken
   * @param expiresIn
   */
  void updateAccessToken(String accessToken, int expiresIn);

  String getJsapiTicket();

  boolean isJsapiTicketExpired();

  /**
   * 强制将jsapi ticket过期掉
   */
  void expireJsapiTicket();

  /**
   * 应该是线程安全的
   * @param jsapiTicket
   */
  void updateJsapiTicket(String jsapiTicket, int expiresInSeconds);

  String getCardApiTicket();

  boolean isCardApiTicketExpired();

  /**
   * 强制将卡券api ticket过期掉
   */
  void expireCardApiTicket();

  /**
   * 应该是线程安全的
   * @param cardApiTicket
   */
  void updateCardApiTicket(String cardApiTicket, int expiresInSeconds);

  String getAppId();

  String getSecret();

  String getPartnerId();
  
  String getPartnerKey();

  String getToken();

  String getAesKey();

  long getExpiresTime();

  String getOauth2redirectUri();

  String getHttpProxyHost();

  int getHttpProxyPort();

  String getHttpProxyUsername();

  String getHttpProxyPassword();
  
  File getTmpDirFile();

  SSLContext getSSLContext();

  /**
   * http client builder
   * @return ApacheHttpClientBuilder
   */
  ApacheHttpClientBuilder getApacheHttpClientBuilder();
}
