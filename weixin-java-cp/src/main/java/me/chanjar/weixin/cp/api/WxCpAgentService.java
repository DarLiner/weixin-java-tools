package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpAgent;

/**
 * <pre>
 *  管理企业号应用
 *  Created by huansinho on 2018/4/13.
 * </pre>
 *
 * @author <a href="https://github.com/huansinho">huansinho</a>
 */
public interface WxCpAgentService {

  /**
   * <pre>
   * 获取企业号应用信息
   * 该API用于获取企业号某个应用的基本信息，包括头像、昵称、帐号类型、认证类型、可见范围等信息
   * 详情请见: http://qydev.weixin.qq.com/wiki/index.php?title=获取企业号应用
   * </pre>
   *
   * @param agentId 企业应用的id
   * @return 部门id
   */
  WxCpAgent get(Integer agentId) throws WxErrorException;

}
