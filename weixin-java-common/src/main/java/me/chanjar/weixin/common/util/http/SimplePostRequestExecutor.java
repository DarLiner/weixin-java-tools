package me.chanjar.weixin.common.util.http;

import me.chanjar.weixin.common.util.http.apache.ApacheSimplePostRequestExecutor;
import me.chanjar.weixin.common.util.http.jodd.JoddSimplePostRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkSimplePostRequestExecutor;

/**
 * 用装饰模式实现
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author Daniel Qian
 */
public abstract class SimplePostRequestExecutor<H, P> implements RequestExecutor<String, String> {
  protected RequestHttp<H, P> requestHttp;

  public SimplePostRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case apacheHttp:
        return new ApacheSimplePostRequestExecutor(requestHttp);
      case joddHttp:
        return new JoddSimplePostRequestExecutor(requestHttp);
      case okHttp:
        return new OkSimplePostRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
