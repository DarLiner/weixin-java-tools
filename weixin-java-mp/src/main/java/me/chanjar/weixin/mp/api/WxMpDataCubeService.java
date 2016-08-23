package me.chanjar.weixin.mp.api;

import java.util.Date;
import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUserCumulate;
import me.chanjar.weixin.mp.bean.result.WxMpUserSummary;

/**
 * 统计分析相关接口 
 *  Created by Binary Wang on 2016/8/23.
 * @author binarywang (https://github.com/binarywang)
 */
public interface WxMpDataCubeService {
  /**
   * <pre>
   * 获取用户增减数据
   * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
   * </pre>
   *
   * @param beginDate 最大时间跨度7天
   * @param endDate   endDate不能早于begingDate
   */
  List<WxMpUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取累计用户数据
   * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
   * </pre>
   *
   * @param beginDate 最大时间跨度7天
   * @param endDate   endDate不能早于begingDate
   */
  List<WxMpUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException;
}
