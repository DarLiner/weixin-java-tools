package me.chanjar.weixin.common.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * 用装饰模式实现
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author Daniel Qian
 */
public class SimplePostRequestExecutor implements RequestExecutor<String, String> {

  @Override
  public String execute(RequestHttp requestHttp, String uri, String postEntity) throws WxErrorException, IOException {
    if (requestHttp.getHttpClient() instanceof CloseableHttpClient) {
      CloseableHttpClient httpClient = (CloseableHttpClient) requestHttp.getHttpClient();
      HttpHost httpProxy = (HttpHost) requestHttp.getHttpProxy();
      return executeApache(httpClient, httpProxy, uri, postEntity);
    }
    if (requestHttp.getHttpClient() instanceof HttpConnectionProvider) {
      HttpConnectionProvider provider = (HttpConnectionProvider) requestHttp.getHttpClient();
      ProxyInfo proxyInfo = (ProxyInfo) requestHttp.getHttpProxy();
      return executeJodd(provider, proxyInfo, uri, postEntity);
    } else {
      //这里需要抛出异常，需要优化
      return null;
    }
  }

  private String executeApache(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String postEntity) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    if (postEntity != null) {
      StringEntity entity = new StringEntity(postEntity, Consts.UTF_8);
      httpPost.setEntity(entity);
    }

    try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      if (responseContent.isEmpty()) {
        throw new WxErrorException(
          WxError.newBuilder().setErrorCode(9999).setErrorMsg("无响应内容")
            .build());
      }

      if (responseContent.startsWith("<xml>")) {
        //xml格式输出直接返回
        return responseContent;
      }

      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return responseContent;
    } finally {
      httpPost.releaseConnection();
    }
  }


  private String executeJodd(HttpConnectionProvider provider, ProxyInfo proxyInfo, String uri, String postEntity) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (proxyInfo != null) {
      provider.useProxy(proxyInfo);
    }
    request.withConnectionProvider(provider);
    if (postEntity != null) {
      request.bodyText(postEntity);
    }
    HttpResponse response = request.send();

    String responseContent = response.bodyText();
    if (responseContent.isEmpty()) {
      throw new WxErrorException(
        WxError.newBuilder().setErrorCode(9999).setErrorMsg("无响应内容")
          .build());
    }

    if (responseContent.startsWith("<xml>")) {
      //xml格式输出直接返回
      return responseContent;
    }

    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    return responseContent;
  }


}
