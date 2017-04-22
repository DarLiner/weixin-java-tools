package me.chanjar.weixin.common.util.http;

/**
 * Created by ecoolper on 2017/4/22.
 */
public interface RequestHttp {

  /**
   * 返回httpClient
   * @return
   */
  Object getRequestHttpClient();

  /**
   * 返回httpProxy
   * @return
   */
  Object getRequestHttpProxy();

}
