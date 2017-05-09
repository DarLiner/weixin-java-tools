package me.chanjar.weixin.common.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.apache.ApacheSimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;

import me.chanjar.weixin.common.util.http.jodd.JoddSimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkSimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkhttpProxyInfo;
import okhttp3.*;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author Daniel Qian
 */
public abstract class SimpleGetRequestExecutor<H, P> implements RequestExecutor<String, String> {
  protected RequestHttp<H,P> requestHttp;

  public SimpleGetRequestExecutor(RequestHttp requestHttp){
    this.requestHttp =requestHttp;
  }


  public static RequestExecutor<String, String>  create(RequestHttp requestHttp){
    switch(requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheSimpleGetRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddSimpleGetRequestExecutor(requestHttp);
      case okHttp:
        return new OkSimpleGetRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
