package me.chanjar.weixin.common.util.http.okhttp;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.MediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class OkHttpMediaDownloadRequestExecutor extends MediaDownloadRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  public OkHttpMediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
    super(requestHttp, tmpDirFile);
  }

  @Override
  public File execute(String uri, String queryParam) throws WxErrorException, IOException {
    logger.debug("OkHttpMediaDownloadRequestExecutor is running");
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    //得到httpClient
    OkHttpClient client = requestHttp.getRequestHttpClient();

    Request request = new Request.Builder().url(uri).get().build();

    Response response = client.newCall(request).execute();

    String contentType = response.header("Content-Type");
    if (contentType != null && contentType.startsWith("application/json")) {
      // application/json; encoding=utf-8 下载媒体文件出错
      throw new WxErrorException(WxError.fromJson(response.body().string()));
    }

    String fileName = getFileName(response);
    if (StringUtils.isBlank(fileName)) {
      return null;
    }
    String[] nameAndExt = fileName.split("\\.");
    File file = File.createTempFile(nameAndExt[0], nameAndExt[1], super.tmpDirFile);
    try (BufferedSink sink = Okio.buffer(Okio.sink(file))) {
      sink.writeAll(response.body().source());
    }
    return file;
  }

  private String getFileName(Response response) throws WxErrorException {
    String content = response.header("Content-disposition");
    if (content == null || content.length() == 0) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("无法获取到文件名").build());
    }

    Pattern p = Pattern.compile(".*filename=\"(.*)\"");
    Matcher m = p.matcher(content);
    if (m.matches()) {
      return m.group(1);
    }
    throw new WxErrorException(WxError.newBuilder().setErrorMsg("无法获取到文件名").build());
  }

}
