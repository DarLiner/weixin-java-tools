package me.chanjar.weixin.mp.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.MimeTypes;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;

import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.apache.InputStreamResponseHandler;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;
import me.chanjar.weixin.common.util.http.okhttp.OkhttpProxyInfo;

import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.util.http.apache.ApacheQrCodeRequestExecutor;
import me.chanjar.weixin.mp.util.http.jodd.JoddQrCodeRequestExecutor;
import me.chanjar.weixin.mp.util.http.okhttp.OkhttpQrCodeRequestExecutor;
import okhttp3.*;

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
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 获得QrCode图片 请求执行器
 *
 * @author chanjarster
 */
public abstract class QrCodeRequestExecutor<H, P> implements RequestExecutor<File,WxMpQrCodeTicket> {
  protected RequestHttp<H, P> requestHttp;

  public QrCodeRequestExecutor(RequestHttp requestHttp){
    this.requestHttp =requestHttp;
  }

  public static RequestExecutor<File,WxMpQrCodeTicket> create(RequestHttp requestHttp){
    switch (requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheQrCodeRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddQrCodeRequestExecutor(requestHttp);
      case okHttp:
        return new OkhttpQrCodeRequestExecutor(requestHttp);
      default:
        //TODO 需要优化，最好抛出异常
        return null;
    }
  }

}
