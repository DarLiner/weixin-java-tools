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

import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.util.http.apache.ApacheMediaImgUploadRequestExecutor;
import me.chanjar.weixin.mp.util.http.jodd.JoddMediaImgUploadRequestExecutor;
import me.chanjar.weixin.mp.util.http.okhttp.OkhttpMediaImgUploadRequestExecutor;
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

/**
 * @author miller
 */
public abstract class MediaImgUploadRequestExecutor<H,P> implements RequestExecutor<WxMediaImgUploadResult, File> {
  protected RequestHttp<H,P> requestHttp;
  public MediaImgUploadRequestExecutor(RequestHttp requestHttp){
    this.requestHttp =requestHttp;
  }

  public static RequestExecutor<WxMediaImgUploadResult, File> create(RequestHttp requestHttp){
    switch (requestHttp.getRequestType()){
      case apacheHttp:
        return new ApacheMediaImgUploadRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddMediaImgUploadRequestExecutor(requestHttp);
      case okHttp:
        return new OkhttpMediaImgUploadRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
