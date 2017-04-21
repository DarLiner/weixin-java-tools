package me.chanjar.weixin.mp.util.http.jodd;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;

import java.io.IOException;

public class MaterialDeleteRequestExecutor implements RequestExecutor<Boolean, HttpConnectionProvider, ProxyInfo, String> {


  public MaterialDeleteRequestExecutor() {
    super();
  }

  @Override
  public Boolean execute(HttpConnectionProvider httpclient, ProxyInfo httpProxy, String uri, String materialId) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (httpProxy != null) {
      httpclient.useProxy(httpProxy);
    }
    request.withConnectionProvider(httpclient);

    request.query("media_id", materialId);
    HttpResponse response = request.send();
    String responseContent = response.bodyText();
    WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    } else {
      return true;
    }
  }

}
