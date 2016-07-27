package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpGroup;

import java.util.List;

/**
 * 用户分组相关操作接口
 * @author Binary Wang
 *
 */
public interface WxMpGroupService {


  /**
   * <pre>
   * 分组管理接口 - 创建分组
   * 最多支持创建500个分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   *
   * @param name 分组名字（30个字符以内）
   */
  WxMpGroup groupCreate(String name) throws WxErrorException;

  /**
   * <pre>
   * 分组管理接口 - 查询所有分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   */
  List<WxMpGroup> groupGet() throws WxErrorException;

  /**
   * <pre>
   * 分组管理接口 - 查询用户所在分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   *
   * @param openid 微信用户的openid
   */
  long userGetGroup(String openid) throws WxErrorException;

  /**
   * <pre>
   * 分组管理接口 - 修改分组名
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   *
   * 如果id为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   *
   * @param group 要更新的group，group的id,name必须设置
   */
  void groupUpdate(WxMpGroup group) throws WxErrorException;

  /**
   * <pre>
   * 分组管理接口 - 移动用户分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   *
   * 如果to_groupid为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   *
   * @param openid     用户openid
   * @param to_groupid 移动到的分组id
   */
  void userUpdateGroup(String openid, long to_groupid) throws WxErrorException;
}
