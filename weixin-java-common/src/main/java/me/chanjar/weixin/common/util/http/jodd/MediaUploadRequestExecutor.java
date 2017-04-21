package me.chanjar.weixin.common.util.http.jodd;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;

import java.io.File;
import java.io.IOException;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 *
 * @author Daniel Qian
 */
public class MediaUploadRequestExecutor implements RequestExecutor<WxMediaUploadResult, HttpConnectionProvider, ProxyInfo, File> {

  @Override
  public WxMediaUploadResult execute(HttpConnectionProvider provider, ProxyInfo httpProxy, String uri, File file) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (httpProxy != null) {
      provider.useProxy(httpProxy);
    }
    request.withConnectionProvider(provider);
    request.form("media", file);
    HttpResponse response = request.send();
    String responseContent =response.bodyText();
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    return WxMediaUploadResult.fromJson(responseContent);
  }

}
