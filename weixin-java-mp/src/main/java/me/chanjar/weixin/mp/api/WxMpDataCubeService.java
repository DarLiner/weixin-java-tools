package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUserCumulate;
import me.chanjar.weixin.mp.bean.result.WxMpUserSummary;

import java.util.Date;
import java.util.List;

/**
 * 统计分析相关接口 
 *  Created by Binary Wang on 2016/8/23.
 * @author binarywang (https://github.com/binarywang)
 */
public interface WxMpDataCubeService {
  /**
   * <pre>
   * 获取用户增减数据
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">用户分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getusersummary?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度7天，endDate不能早于begingDate
   */
  List<WxMpUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取累计用户数据
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">用户分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度7天，endDate不能早于begingDate
   */
  List<WxMpUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException;
}
