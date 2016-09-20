package me.chanjar.weixin.mp.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserBlackListService;
import me.chanjar.weixin.mp.bean.result.WxMpUserBlackListGetResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author miller
 */
public class WxMpUserBlackListServiceImpl implements WxMpUserBlackListService {
  private static final String API_BLACK_LIST_PREFIX = "https://api.weixin.qq.com/cgi-bin/tags/members";
  private WxMpService wxMpService;

  public WxMpUserBlackListServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxMpUserBlackListGetResult blackList(String nextOpenid) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("begin_openid", nextOpenid);
    String url = API_BLACK_LIST_PREFIX + "/getblacklist";
    String responseContent = this.wxMpService.execute(new SimplePostRequestExecutor(), url, jsonObject.toString());
    return WxMpUserBlackListGetResult.fromJson(responseContent);
  }

  @Override
  public void pushToBlackList(List<String> openIdList) throws WxErrorException {
    Map<String, Object> map = new HashMap<>();
    map.put("openid_list", openIdList);
    String url = API_BLACK_LIST_PREFIX + "/batchblacklist";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url, new Gson().toJson(map));
  }

  @Override
  public void pullFromBlackList(List<String> openIdList) throws WxErrorException {
    Map<String, Object> map = new HashMap<>();
    map.put("openid_list", openIdList);
    String url = API_BLACK_LIST_PREFIX + "/batchunblacklist";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url, new Gson().toJson(map));
  }
}
