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
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.util.http.apache.ApacheMaterialUploadRequestExecutor;
import me.chanjar.weixin.mp.util.http.jodd.JoddMaterialUploadRequestExecutor;
import me.chanjar.weixin.mp.util.http.okhttp.OkhttpMaterialUploadRequestExecutor;
import okhttp3.*;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public abstract class MaterialUploadRequestExecutor<H,P> implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {
  protected RequestHttp<H,P> requestHttp;
  public MaterialUploadRequestExecutor(RequestHttp requestHttp){
    this.requestHttp =requestHttp;
  }

  public static RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> create(RequestHttp requestHttp){
    switch (requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheMaterialUploadRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddMaterialUploadRequestExecutor(requestHttp);
      case okHttp:
        return new OkhttpMaterialUploadRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
