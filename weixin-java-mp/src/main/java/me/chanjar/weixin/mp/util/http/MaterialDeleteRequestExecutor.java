package me.chanjar.weixin.mp.util.http;

import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.mp.util.http.apache.ApacheMaterialDeleteRequestExecutor;
import me.chanjar.weixin.mp.util.http.jodd.JoddMaterialDeleteRequestExecutor;
import me.chanjar.weixin.mp.util.http.okhttp.OkhttpMaterialDeleteRequestExecutor;

public abstract class MaterialDeleteRequestExecutor<H, P> implements RequestExecutor<Boolean, String> {
  protected RequestHttp<H, P> requestHttp;

  public MaterialDeleteRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
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
