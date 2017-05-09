package me.chanjar.weixin.common.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.apache.ApacheMediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;

import me.chanjar.weixin.common.util.http.jodd.JoddMediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkMediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkhttpProxyInfo;
import okhttp3.*;

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
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 *
 * @author Daniel Qian
 */
public abstract class MediaUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaUploadResult, File> {
  protected RequestHttp<H,P> requestHttp;

  public MediaUploadRequestExecutor(RequestHttp requestHttp){
    this.requestHttp =requestHttp;
  }

  public static RequestExecutor<WxMediaUploadResult, File>  create(RequestHttp requestHttp){
    switch (requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheMediaUploadRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddMediaUploadRequestExecutor(requestHttp);
      case okHttp:
        return new OkMediaUploadRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
