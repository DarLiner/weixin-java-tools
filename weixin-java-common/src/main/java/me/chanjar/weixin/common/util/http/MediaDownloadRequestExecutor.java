package me.chanjar.weixin.common.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.apache.ApacheMediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.apache.InputStreamResponseHandler;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;
import me.chanjar.weixin.common.util.http.jodd.JoddMediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkMediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkhttpProxyInfo;
import okhttp3.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 视频文件不支持下载
 *
 * @author Daniel Qian
 */
public abstract class MediaDownloadRequestExecutor<H, P> implements RequestExecutor<File, String> {

  public static RequestExecutor<File, String> create(RequestHttp requestHttp, File tmpDirFile){
    switch (requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case joddHttp:
        return new JoddMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case okHttp:
        return new OkMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      default:
        return null;
    }
  }

  protected RequestHttp<H, P> requestHttp;
  protected File tmpDirFile;

  public MediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
    this.tmpDirFile = tmpDirFile;
  }

}
