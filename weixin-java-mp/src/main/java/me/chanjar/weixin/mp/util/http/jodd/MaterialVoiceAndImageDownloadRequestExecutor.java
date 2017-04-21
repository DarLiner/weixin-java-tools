package me.chanjar.weixin.mp.util.http.jodd;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MaterialVoiceAndImageDownloadRequestExecutor implements RequestExecutor<InputStream, HttpConnectionProvider, ProxyInfo, String> {


  public MaterialVoiceAndImageDownloadRequestExecutor() {
    super();
  }

  public MaterialVoiceAndImageDownloadRequestExecutor(File tmpDirFile) {
    super();
  }

  @Override
  public InputStream execute(HttpConnectionProvider provider, ProxyInfo httpProxy, String uri, String materialId) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (httpProxy != null) {
      provider.useProxy(httpProxy);
    }
    request.withConnectionProvider(provider);

    request.query("media_id", materialId);
    HttpResponse response = request.send();

    InputStream inputStream = new ByteArrayInputStream(response.bodyBytes());
    // 下载媒体文件出错
    byte[] responseContent = IOUtils.toByteArray(inputStream);
    String responseContentString = new String(responseContent, "UTF-8");
    if (responseContentString.length() < 100) {
      try {
        WxError wxError = WxGsonBuilder.create().fromJson(responseContentString, WxError.class);
        if (wxError.getErrorCode() != 0) {
          throw new WxErrorException(wxError);
        }
      } catch (com.google.gson.JsonSyntaxException ex) {
        return new ByteArrayInputStream(responseContent);
      }
    }
    return new ByteArrayInputStream(responseContent);
  }

}
