package me.chanjar.weixin.cp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpAgentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpAgent;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;


/**
 * <pre>
 *  管理企业号应用
 *  Created by huansinho on 2018/4/13.
 * </pre>
 *
 * @author <a href="https://github.com/huansinho">huansinho</a>
 */
public class WxCpAgentServiceImpl implements WxCpAgentService {
  private WxCpService mainService;

  public WxCpAgentServiceImpl(WxCpService mainService) {
    this.mainService = mainService;
  }

  @Override
  public WxCpAgent get(Integer agentId) throws WxErrorException {

    String url = "https://qyapi.weixin.qq.com/cgi-bin/agent/get";
    if (agentId != null) {
      url += "?agentid=" + agentId;
    } else {
      throw new IllegalArgumentException("缺少agentid参数");
    }
    String responseContent = this.mainService.get(url, null);
    return WxCpAgent.fromJson(responseContent);
  }

}
