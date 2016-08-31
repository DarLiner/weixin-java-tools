package me.chanjar.weixin.mp.api.impl;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.mp.api.WxMpGroupService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpGroup;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * Created by Binary Wang on 2016/7/21.
 */
public class WxMpGroupServiceImpl implements WxMpGroupService {
  private static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/groups";
  private WxMpService wxMpService;

  public WxMpGroupServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxMpGroup groupCreate(String name) throws WxErrorException {
    String url = API_URL_PREFIX + "/create";
    JsonObject json = new JsonObject();
    JsonObject groupJson = new JsonObject();
    json.add("group", groupJson);
    groupJson.addProperty("name", name);

    String responseContent = this.wxMpService.execute(
            new SimplePostRequestExecutor(),
            url,
            json.toString());
    return WxMpGroup.fromJson(responseContent);
  }

  @Override
  public List<WxMpGroup> groupGet() throws WxErrorException {
    String url = API_URL_PREFIX + "/get";
    String responseContent = this.wxMpService.execute(new SimpleGetRequestExecutor(), url, null);
    /*
     * 操蛋的微信API，创建时返回的是 { group : { id : ..., name : ...} }
     * 查询时返回的是 { groups : [ { id : ..., name : ..., count : ... }, ... ] }
     */
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("groups"),
            new TypeToken<List<WxMpGroup>>() {
            }.getType());
  }

  @Override
  public long userGetGroup(String openid) throws WxErrorException {
    String url = API_URL_PREFIX + "/getid";
    JsonObject o = new JsonObject();
    o.addProperty("openid", openid);
    String responseContent = this.wxMpService.execute(new SimplePostRequestExecutor(), url, o.toString());
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return GsonHelper.getAsLong(tmpJsonElement.getAsJsonObject().get("groupid"));
  }

  @Override
  public void groupUpdate(WxMpGroup group) throws WxErrorException {
    String url = API_URL_PREFIX + "/update";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url, group.toJson());
  }

  @Override
  public void userUpdateGroup(String openid, long to_groupid) throws WxErrorException {
    String url = API_URL_PREFIX + "/members/update";
    JsonObject json = new JsonObject();
    json.addProperty("openid", openid);
    json.addProperty("to_groupid", to_groupid);
    this.wxMpService.execute(new SimplePostRequestExecutor(), url, json.toString());
  }

}
