package me.chanjar.weixin.mp.util.http;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;

import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.apache.InputStreamResponseHandler;
import me.chanjar.weixin.common.util.http.okhttp.OkhttpProxyInfo;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import me.chanjar.weixin.mp.util.http.apache.ApacheMaterialVoiceAndImageDownloadRequestExecutor;
import me.chanjar.weixin.mp.util.http.jodd.JoddMaterialVoiceAndImageDownloadRequestExecutor;
import me.chanjar.weixin.mp.util.http.okhttp.OkhttpMaterialVoiceAndImageDownloadRequestExecutor;
import okhttp3.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class MaterialVoiceAndImageDownloadRequestExecutor<H, P> implements RequestExecutor<InputStream, String> {
  protected RequestHttp<H, P> requestHttp;
  protected File tmpDirFile;


  public MaterialVoiceAndImageDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
    this.requestHttp = requestHttp;
    this.tmpDirFile = tmpDirFile;
  }


  public static RequestExecutor<InputStream, String> create(RequestHttp requestHttp, File tmpDirFile) {
    switch (requestHttp.getRequestType()) {
      case apacheHttp:
        return new ApacheMaterialVoiceAndImageDownloadRequestExecutor(requestHttp, tmpDirFile);
      case joddHttp:
        return new JoddMaterialVoiceAndImageDownloadRequestExecutor(requestHttp, tmpDirFile);
      case okHttp:
        return new OkhttpMaterialVoiceAndImageDownloadRequestExecutor(requestHttp, tmpDirFile);
      default:
        return null;
    }
  }


}
