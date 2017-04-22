package me.chanjar.weixin.mp.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialVideoInfoResult;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MaterialVideoInfoRequestExecutor implements RequestExecutor<WxMpMaterialVideoInfoResult, String> {

  public MaterialVideoInfoRequestExecutor() {
    super();
  }

  @Override
  public WxMpMaterialVideoInfoResult execute(RequestHttp requestHttp, String uri, String materialId) throws WxErrorException, IOException {
    if (requestHttp.getRequestHttpClient() instanceof CloseableHttpClient) {
      CloseableHttpClient httpClient = (CloseableHttpClient) requestHttp.getRequestHttpClient();
      HttpHost httpProxy = (HttpHost) requestHttp.getRequestHttpProxy();
      return executeApache(httpClient, httpProxy, uri, materialId);
    }
    if (requestHttp.getRequestHttpClient() instanceof HttpConnectionProvider) {
      HttpConnectionProvider provider = (HttpConnectionProvider) requestHttp.getRequestHttpClient();
      ProxyInfo proxyInfo = (ProxyInfo) requestHttp.getRequestHttpProxy();
      return executeJodd(provider, proxyInfo, uri, materialId);
    } else {
      //这里需要抛出异常，需要优化
      return null;
    }
  }


  private WxMpMaterialVideoInfoResult executeJodd(HttpConnectionProvider provider, ProxyInfo proxyInfo, String uri, String materialId) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (proxyInfo != null) {
      provider.useProxy(proxyInfo);
    }
    request.withConnectionProvider(provider);

    request.query("media_id", materialId);
    HttpResponse response = request.send();
    String responseContent = response.bodyText();
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    } else {
      return WxMpMaterialVideoInfoResult.fromJson(responseContent);
    }
  }

  private WxMpMaterialVideoInfoResult executeApache(CloseableHttpClient httpclient, HttpHost httpProxy, String uri,
                                                    String materialId) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    Map<String, String> params = new HashMap<>();
    params.put("media_id", materialId);
    httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
    try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      } else {
        return WxMpMaterialVideoInfoResult.fromJson(responseContent);
      }
    } finally {
      httpPost.releaseConnection();
    }
  }


}
