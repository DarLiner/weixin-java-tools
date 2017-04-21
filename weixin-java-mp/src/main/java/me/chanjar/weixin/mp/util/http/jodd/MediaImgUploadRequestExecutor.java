package me.chanjar.weixin.mp.util.http.jodd;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;

import java.io.File;
import java.io.IOException;

/**
 * @author miller
 */
public class MediaImgUploadRequestExecutor implements RequestExecutor<WxMediaImgUploadResult, HttpConnectionProvider, ProxyInfo, File> {
  @Override
  public WxMediaImgUploadResult execute(HttpConnectionProvider provider, ProxyInfo httpProxy, String uri, File data) throws WxErrorException, IOException {
    if (data == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("文件对象为空").build());
    }

    HttpRequest request = HttpRequest.post(uri);
    if (httpProxy != null) {
      provider.useProxy(httpProxy);
    }
    request.withConnectionProvider(provider);

    request.form("media", data);
    HttpResponse response =request.send();

    String responseContent =response.bodyText();
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }

    return WxMediaImgUploadResult.fromJson(responseContent);
  }
}
