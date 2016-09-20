package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUserBlackListGetResult;

import java.util.List;

/**
 * @author miller
 */
public interface WxMpUserBlackListService {
  /**
   * <pre>
   * 获取公众号的黑名单列表
   * 详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
   * </pre>
   */
  WxMpUserBlackListGetResult blackList(String nextOpenid) throws WxErrorException;

  /**
   * <pre>
   *   拉黑用户
   *   详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
   * </pre>
   */
  void pushToBlackList(List<String> openIdList) throws WxErrorException;

  /**
   * <pre>
   *   取消拉黑用户
   *   详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
   * </pre>
   */
  void pullFromBlackList(List<String> openIdList) throws WxErrorException;
}
