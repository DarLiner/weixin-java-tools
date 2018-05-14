package me.chanjar.weixin.common.api;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * WxErrorException处理器.
 */
public interface WxErrorExceptionHandler {

  void handle(WxErrorException e);

}
