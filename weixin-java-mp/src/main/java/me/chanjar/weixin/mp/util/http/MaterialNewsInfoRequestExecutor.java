package me.chanjar.weixin.mp.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;

import me.chanjar.weixin.common.util.http.okhttp.OkhttpProxyInfo;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.util.http.apache.ApacheMaterialNewsInfoRequestExecutor;
import me.chanjar.weixin.mp.util.http.jodd.JoddMaterialNewsInfoRequestExecutor;
import me.chanjar.weixin.mp.util.http.okhttp.OkhttpMaterialNewsInfoRequestExecutor;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
import okhttp3.*;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class MaterialNewsInfoRequestExecutor<H,P> implements RequestExecutor<WxMpMaterialNews, String> {
  protected RequestHttp<H,P> requestHttp;

  public MaterialNewsInfoRequestExecutor(RequestHttp requestHttp){
    this.requestHttp =requestHttp;
  }

  public static RequestExecutor<WxMpMaterialNews, String> create(RequestHttp requestHttp){
    switch (requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheMaterialNewsInfoRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddMaterialNewsInfoRequestExecutor(requestHttp);
      case okHttp:
        return new OkhttpMaterialNewsInfoRequestExecutor(requestHttp);
      default:
        //TODO 需要优化抛出异常
        return null;
    }
  }

}
