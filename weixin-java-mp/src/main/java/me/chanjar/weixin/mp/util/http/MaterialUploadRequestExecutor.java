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
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class MaterialUploadRequestExecutor implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {

  @Override
  public WxMpMaterialUploadResult execute(RequestHttp requestHttp, String uri, WxMpMaterial material) throws WxErrorException, IOException {
    if (requestHttp.getRequestHttpClient() instanceof CloseableHttpClient) {
      CloseableHttpClient httpClient = (CloseableHttpClient) requestHttp.getRequestHttpClient();
      HttpHost httpProxy = (HttpHost) requestHttp.getRequestHttpProxy();
      return executeApache(httpClient, httpProxy, uri, material);
    }
    if (requestHttp.getRequestHttpClient() instanceof HttpConnectionProvider) {
      HttpConnectionProvider provider = (HttpConnectionProvider) requestHttp.getRequestHttpClient();
      ProxyInfo proxyInfo = (ProxyInfo) requestHttp.getRequestHttpProxy();
      return executeJodd(provider, proxyInfo, uri, material);
    } else {
      //这里需要抛出异常，需要优化
      return null;
    }
  }

  private WxMpMaterialUploadResult executeJodd(HttpConnectionProvider provider, ProxyInfo httpProxy, String uri, WxMpMaterial material) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (httpProxy != null) {
      provider.useProxy(httpProxy);
    }
    request.withConnectionProvider(provider);

    if (material == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("非法请求，material参数为空").build());
    }

    File file = material.getFile();
    if (file == null || !file.exists()) {
      throw new FileNotFoundException();
    }
    request.form("media", file);
    Map<String, String> form = material.getForm();
    if (material.getForm() != null) {
      request.form("description", WxGsonBuilder.create().toJson(form));
    }

    HttpResponse response = request.send();
    String responseContent = response.bodyText();
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    } else {
      return WxMpMaterialUploadResult.fromJson(responseContent);
    }
  }

  private WxMpMaterialUploadResult executeApache(CloseableHttpClient httpclient, HttpHost httpProxy, String uri,
                                                 WxMpMaterial material) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (httpProxy != null) {
      RequestConfig response = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(response);
    }

    if (material == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("非法请求，material参数为空").build());
    }

    File file = material.getFile();
    if (file == null || !file.exists()) {
      throw new FileNotFoundException();
    }

    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder
      .addBinaryBody("media", file)
      .setMode(HttpMultipartMode.RFC6532);
    Map<String, String> form = material.getForm();
    if (material.getForm() != null) {
      multipartEntityBuilder.addTextBody("description", WxGsonBuilder.create().toJson(form));
    }

    httpPost.setEntity(multipartEntityBuilder.build());
    httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

    try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      } else {
        return WxMpMaterialUploadResult.fromJson(responseContent);
      }
    } finally {
      httpPost.releaseConnection();
    }
  }

}
