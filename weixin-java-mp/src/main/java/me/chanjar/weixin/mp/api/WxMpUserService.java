package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserCumulate;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.result.WxMpUserSummary;

import java.util.Date;
import java.util.List;

/**
 * 用户管理和统计相关操作接口
 *
 * @author Binary Wang
 */
public interface WxMpUserService {

  /**
   * <pre>
   * 设置用户备注名接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=设置用户备注名接口
   * </pre>
   *
   * @param openid 用户openid
   * @param remark 备注名
   */
  public void userUpdateRemark(String openid, String remark) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取用户基本信息
   * </pre>
   *
   * @param openid 用户openid
   * @param lang   语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
   */
  public WxMpUser userInfo(String openid, String lang) throws WxErrorException;

  /**
   * <pre>
   * 获取关注者列表
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
   * </pre>
   *
   * @param next_openid 可选，第一个拉取的OPENID，null为从头开始拉取
   */
  public WxMpUserList userList(String next_openid) throws WxErrorException;

  /**
   * <pre>
   * 获取用户增减数据
   * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
   * </pre>
   *
   * @param beginDate 最大时间跨度7天
   * @param endDate   endDate不能早于begingDate
   */
  List<WxMpUserSummary> dataCubeUserSummary(Date beginDate, Date endDate) throws WxErrorException;

  /**
   * <pre>
   * 获取累计用户数据
   * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
   * </pre>
   *
   * @param beginDate 最大时间跨度7天
   * @param endDate   endDate不能早于begingDate
   */
  List<WxMpUserCumulate> dataCubeUserCumulate(Date beginDate, Date endDate) throws WxErrorException;
}
