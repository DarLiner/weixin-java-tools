package me.chanjar.weixin.common.util.http.jodd;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.IOException;

/**
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author Daniel Qian
 */
public class SimplePostRequestExecutor implements RequestExecutor<String, String> {

  @Override
  public String execute(ProxyInfo httpProxy, String uri, String postEntity) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (httpProxy != null) {
      SocketHttpConnectionProvider provider = new SocketHttpConnectionProvider();
      provider.useProxy(httpProxy);
      request.withConnectionProvider(provider);
    }
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
