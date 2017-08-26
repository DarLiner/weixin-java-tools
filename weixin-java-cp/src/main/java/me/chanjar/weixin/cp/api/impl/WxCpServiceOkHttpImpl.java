package me.chanjar.weixin.cp.api.impl;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.HttpType;
import me.chanjar.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WxCpServiceOkHttpImpl extends WxCpServiceAbstractImpl<OkHttpClient, OkHttpProxyInfo> {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected OkHttpClient httpClient;
  protected OkHttpProxyInfo httpProxy;


  @Override
  public OkHttpClient getRequestHttpClient() {
    return httpClient;
  }

  @Override
  public OkHttpProxyInfo getRequestHttpProxy() {
    return httpProxy;
  }

  @Override
  public HttpType getRequestType() {
    return HttpType.OK_HTTP;
  }

  @Override
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    logger.debug("WxCpServiceOkHttpImpl is running");
    if (this.configStorage.isAccessTokenExpired() || forceRefresh) {
      synchronized (this.globalAccessTokenRefreshLock) {
        if (this.configStorage.isAccessTokenExpired()) {
          String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?"
            + "&corpid=" + this.configStorage.getCorpId()
            + "&corpsecret=" + this.configStorage.getCorpSecret();
          //得到httpClient
          OkHttpClient client = getRequestHttpClient();
          //请求的request
          Request request = new Request.Builder().url(url).get().build();
          Response response = null;
          try {
            response = client.newCall(request).execute();
          } catch (IOException e) {
            e.printStackTrace();
          }
          String resultContent = null;
          try {
            resultContent = response.body().string();
          } catch (IOException e) {
            e.printStackTrace();
          }
          WxError error = WxError.fromJson(resultContent);
          if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
          }
          WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
          this.configStorage.updateAccessToken(accessToken.getAccessToken(),
            accessToken.getExpiresIn());
        }
      }
    }
    return this.configStorage.getAccessToken();
  }

  @Override
  public void initHttp() {
    logger.debug("WxCpServiceOkHttpImpl initHttp");
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    //设置代理
    if (httpProxy != null) {
      clientBuilder.proxy(getRequestHttpProxy().getProxy());

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
    }
    httpClient = clientBuilder.build();
  }

  @Override
  public WxCpConfigStorage getWxCpConfigStorage() {
    return this.configStorage;
  }
}
