package me.chanjar.weixin.common.util.http;

import me.chanjar.weixin.common.util.http.apache.ApacheSimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.jodd.JoddSimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkSimpleGetRequestExecutor;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author Daniel Qian
 */
public abstract class SimpleGetRequestExecutor<H, P> implements RequestExecutor<String, String> {
  protected RequestHttp<H, P> requestHttp;

  public SimpleGetRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }


  public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
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
