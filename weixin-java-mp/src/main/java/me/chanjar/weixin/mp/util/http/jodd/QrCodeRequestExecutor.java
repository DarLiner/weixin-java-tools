package me.chanjar.weixin.mp.util.http.jodd;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.MimeTypes;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 获得QrCode图片 请求执行器
 *
 * @author chanjarster
 */
public class QrCodeRequestExecutor implements RequestExecutor<File, HttpConnectionProvider, ProxyInfo, WxMpQrCodeTicket> {

  @Override
  public File execute(HttpConnectionProvider provider, ProxyInfo httpProxy, String uri,
                      WxMpQrCodeTicket ticket) throws WxErrorException, IOException {
    if (ticket != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?")
        ? "ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8")
        : "&ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
    }


    HttpRequest request = HttpRequest.get(uri);
    if (httpProxy != null) {
      provider.useProxy(httpProxy);
    }
    request.withConnectionProvider(provider);

    HttpResponse response = request.send();
    try (
      InputStream inputStream = new ByteArrayInputStream(response.bodyBytes());) {
      String contentTypeHeader = response.header("Content-Type");
      // 出错
      if (MimeTypes.MIME_TEXT_PLAIN.equals(contentTypeHeader)) {
        String responseContent = response.bodyText();
        throw new WxErrorException(WxError.fromJson(responseContent));
      }
      return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
    }
  }

}
