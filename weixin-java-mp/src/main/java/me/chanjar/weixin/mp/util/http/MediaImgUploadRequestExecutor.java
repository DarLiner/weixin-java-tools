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
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;

/**
 * @author miller
 */
public class MediaImgUploadRequestExecutor implements RequestExecutor<WxMediaImgUploadResult, File> {

  @Override
  public WxMediaImgUploadResult execute(RequestHttp requestHttp, String uri, File data) throws WxErrorException, IOException {
    if (requestHttp.getRequestHttpClient() instanceof CloseableHttpClient) {
      CloseableHttpClient httpClient = (CloseableHttpClient) requestHttp.getRequestHttpClient();
      HttpHost httpProxy = (HttpHost) requestHttp.getRequestHttpProxy();
      return executeApache(httpClient, httpProxy, uri, data);
    }
    if (requestHttp.getRequestHttpClient() instanceof HttpConnectionProvider) {
      HttpConnectionProvider provider = (HttpConnectionProvider) requestHttp.getRequestHttpClient();
      ProxyInfo proxyInfo = (ProxyInfo) requestHttp.getRequestHttpProxy();
      return executeJodd(provider, proxyInfo, uri, data);
    } else {
      //这里需要抛出异常，需要优化
      return null;
    }

  }


  private WxMediaImgUploadResult executeJodd(HttpConnectionProvider provider, ProxyInfo proxyInfo, String uri, File data) throws WxErrorException, IOException {
    if (data == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("文件对象为空").build());
    }

    HttpRequest request = HttpRequest.post(uri);
    if (proxyInfo != null) {
      provider.useProxy(proxyInfo);
    }
    request.withConnectionProvider(provider);

    request.form("media", data);
    HttpResponse response = request.send();
    String responseContent = response.bodyText();
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }

    return WxMediaImgUploadResult.fromJson(responseContent);
  }

  private WxMediaImgUploadResult executeApache(CloseableHttpClient httpclient, HttpHost httpProxy, String uri,
                                               File data) throws WxErrorException, IOException {
    if (data == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("文件对象为空").build());
    }

    HttpPost httpPost = new HttpPost(uri);
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    HttpEntity entity = MultipartEntityBuilder
      .create()
      .addBinaryBody("media", data)
      .setMode(HttpMultipartMode.RFC6532)
      .build();
    httpPost.setEntity(entity);
    httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

    try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }

      return WxMediaImgUploadResult.fromJson(responseContent);
    }
  }

}
