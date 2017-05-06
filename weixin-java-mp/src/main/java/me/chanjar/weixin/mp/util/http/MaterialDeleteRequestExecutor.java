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
import me.chanjar.weixin.mp.util.http.apache.ApacheMaterialDeleteRequestExecutor;
import me.chanjar.weixin.mp.util.http.jodd.JoddMaterialDeleteRequestExecutor;
import me.chanjar.weixin.mp.util.http.okhttp.OkhttpMaterialDeleteRequestExecutor;
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

public abstract class MaterialDeleteRequestExecutor<H,P> implements RequestExecutor<Boolean, String> {
  protected RequestHttp<H,P> requestHttp;

  public MaterialDeleteRequestExecutor(RequestHttp requestHttp){
    this.requestHttp =requestHttp;
  }

  public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp){
    switch (requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheMaterialDeleteRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddMaterialDeleteRequestExecutor(requestHttp);
      case okHttp:
        return new OkhttpMaterialDeleteRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
