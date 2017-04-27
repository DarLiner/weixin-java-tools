package me.chanjar.weixin.mp.api.impl.okhttp;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.okhttp.OkhttpProxyInfo;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.api.impl.*;
import okhttp3.*;

public class WxMpServiceImpl extends AbstractWxMpService<ConnectionPool, OkhttpProxyInfo> {


  private ConnectionPool httpClient;
  private OkhttpProxyInfo httpProxy;


  @Override
  public ConnectionPool getRequestHttpClient() {
    return httpClient;
  }

  @Override
  public OkhttpProxyInfo getRequestHttpProxy() {
    return httpProxy;
  }


  @Override
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    Lock lock = this.getWxMpConfigStorage().getAccessTokenLock();
    try {
      lock.lock();

      if (forceRefresh) {
        this.getWxMpConfigStorage().expireAccessToken();
      }

      if (this.getWxMpConfigStorage().isAccessTokenExpired()) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
          "&appid=" + this.getWxMpConfigStorage().getAppId() + "&secret="
          + this.getWxMpConfigStorage().getSecret();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectionPool(httpClient);
        //设置代理
        if (httpProxy != null) {
          clientBuilder.proxy(getRequestHttpProxy().getProxy());
        }
        //设置授权
        clientBuilder.authenticator(new Authenticator() {
          @Override
          public Request authenticate(Route route, Response response) throws IOException {
            String credential = Credentials.basic(httpProxy.getProxyUsername(), httpProxy.getProxyPassword());
            return response.request().newBuilder()
              .header("Authorization", credential)
              .build();
          }
        });
        //得到httpClient
        OkHttpClient client = clientBuilder.build();

        Request request =new Request.Builder().url(url).get().build();
        Response response =client.newCall(request).execute();
        String resultContent = response.body().toString();
        WxError error = WxError.fromJson(resultContent);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
        this.getWxMpConfigStorage().updateAccessToken(accessToken.getAccessToken(),
          accessToken.getExpiresIn());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return this.getWxMpConfigStorage().getAccessToken();
  }

  @Override
  public void initHttp() {
    WxMpConfigStorage configStorage = this.getWxMpConfigStorage();

    if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
      httpProxy = new OkhttpProxyInfo(OkhttpProxyInfo.ProxyType.SOCKS5, configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort(), configStorage.getHttpProxyUsername(), configStorage.getHttpProxyPassword());
    }

    httpClient = new ConnectionPool();
  }


}
