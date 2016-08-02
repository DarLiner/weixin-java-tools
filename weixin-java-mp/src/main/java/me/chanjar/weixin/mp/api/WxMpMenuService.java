package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * 菜单相关操作接口
 *
 * @author Binary Wang
 */
public interface WxMpMenuService {

  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   * 如果要创建个性化菜单，请设置matchrule属性
   * 详情请见:http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   */
  void menuCreate(WxMenu menu) throws WxErrorException;

  /**
   * <pre>
   * 自定义菜单删除接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
   * </pre>
   */
  void menuDelete() throws WxErrorException;

  /**
   * <pre>
   * 删除个性化菜单接口
   * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   *
   * @param menuid
   */
  void menuDelete(String menuid) throws WxErrorException;

  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   * </pre>
   */
  WxMenu menuGet() throws WxErrorException;

  /**
   * <pre>
   * 测试个性化菜单匹配结果
   * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   *
   * @param userid 可以是粉丝的OpenID，也可以是粉丝的微信号。
   */
  WxMenu menuTryMatch(String userid) throws WxErrorException;

}
