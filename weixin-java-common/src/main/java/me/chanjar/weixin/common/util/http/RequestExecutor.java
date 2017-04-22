package me.chanjar.weixin.common.util.http;

import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.IOException;

/**
 * http请求执行器
 *
 * @param <T> 返回值类型
 * @param <E> 请求参数类型
 */
public interface RequestExecutor<T, E> {

  /**
   * @param uri  uri
   * @param data 数据
   * @throws WxErrorException
   * @throws IOException
   */
  T execute(RequestHttp requestHttp, String uri, E data) throws WxErrorException, IOException;

}
