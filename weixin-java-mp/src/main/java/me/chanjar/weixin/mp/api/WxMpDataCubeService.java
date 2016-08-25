package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotal;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;

import java.util.Date;
import java.util.List;

/**
 * 统计分析相关接口
 *  Created by Binary Wang on 2016/8/23.
 * @author binarywang (https://github.com/binarywang)
 */
public interface WxMpDataCubeService {
  //*******************用户分析数据接口***********************//

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
  List<WxDataCubeUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException;

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
  List<WxDataCubeUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException;

  //*******************图文分析数据接口***********************//

  /**
   * <pre>
   * 获取图文群发每日数据（getarticlesummary）
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getarticlesummary?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度1天，endDate不能早于begingDate
   */
  List<WxDataCubeArticleResult> getArticleSummary(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取图文群发总数据（getarticletotal）
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度1天，endDate不能早于begingDate
   */
  List<WxDataCubeArticleTotal> getArticleTotal(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取图文统计数据（getuserread）
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getuserread?access_token=ACCESS_TOKEN
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度3天，endDate不能早于begingDate
   */
  List<WxDataCubeArticleResult> getUserRead(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取图文统计分时数据（getuserreadhour）
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getuserreadhour?access_token=ACCESS_TOKEN
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度1天，endDate不能早于begingDate
   */
  List<WxDataCubeArticleResult> getUserReadHour(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取图文分享转发数据（getusershare）
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getusershare?access_token=ACCESS_TOKEN
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度7天，endDate不能早于begingDate
   */
  List<WxDataCubeArticleResult> getUserShare(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取图文分享转发分时数据（getusersharehour）
   * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
   * 接口url格式：https://api.weixin.qq.com/datacube/getusersharehour?access_token=ACCESS_TOKEN
   *
   * @param beginDate 开始时间
   * @param endDate   最大时间跨度1天，endDate不能早于begingDate
   */
  List<WxDataCubeArticleResult> getUserShareHour(Date beginDate, Date endDate) throws WxErrorException;

}
