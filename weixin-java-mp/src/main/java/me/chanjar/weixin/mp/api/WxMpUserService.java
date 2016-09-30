package me.chanjar.weixin.mp.api;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpUserQuery;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

/**
 * 用户管理相关操作接口
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
  void userUpdateRemark(String openid, String remark) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取用户基本信息
   * </pre>
   *
   * @param openid 用户openid
   * @param lang 语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
   */
  WxMpUser userInfo(String openid, String lang) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息列表
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=批量获取用户基本信息
   * </pre>
   *
   * @param openids 用户openid列表
   */
  List<WxMpUser> userInfoList(List<String> openids) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息列表
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=批量获取用户基本信息
   * </pre>
   *
   * @param userQuery 详细查询参数
   */
  List<WxMpUser> userInfoList(WxMpUserQuery userQuery) throws WxErrorException;

  /**
   * <pre>
   * 获取关注者列表
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
   * </pre>
   *
   * @param nextOpenid 可选，第一个拉取的OPENID，null为从头开始拉取
   */
  WxMpUserList userList(String nextOpenid) throws WxErrorException;
}
