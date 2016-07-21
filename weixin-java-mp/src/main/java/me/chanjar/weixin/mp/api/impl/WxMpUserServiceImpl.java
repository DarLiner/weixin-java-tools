package me.chanjar.weixin.mp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserCumulate;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.result.WxMpUserSummary;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

/**
 * Created by Binary Wang on 2016/7/21.
 */
public class WxMpUserServiceImpl implements WxMpUserService {
  private static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/user";
  private WxMpService wxMpService;

  public WxMpUserServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public void userUpdateRemark(String openid, String remark) throws WxErrorException {
    String url = API_URL_PREFIX + "/info/updateremark";
    JsonObject json = new JsonObject();
    json.addProperty("openid", openid);
    json.addProperty("remark", remark);
    this.wxMpService.execute(new SimplePostRequestExecutor(), url, json.toString());
  }

  @Override
  public WxMpUser userInfo(String openid, String lang) throws WxErrorException {
    String url = API_URL_PREFIX + "/info";
    lang = lang == null ? "zh_CN" : lang;
    String responseContent = this.wxMpService.execute(new SimpleGetRequestExecutor(), url, "openid=" + openid + "&lang=" + lang);
    return WxMpUser.fromJson(responseContent);
  }

  @Override
  public WxMpUserList userList(String next_openid) throws WxErrorException {
    String url = API_URL_PREFIX + "/get";
    String responseContent = this.wxMpService.execute(new SimpleGetRequestExecutor(), url, next_openid == null ? null : "next_openid=" + next_openid);
    return WxMpUserList.fromJson(responseContent);
  }

  @Override
  public List<WxMpUserSummary> dataCubeUserSummary(Date beginDate, Date endDate) throws WxErrorException {
    String url = "https://api.weixin.qq.com/datacube/getusersummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("list"),
            new TypeToken<List<WxMpUserSummary>>() {
            }.getType());
  }

  @Override
  public List<WxMpUserCumulate> dataCubeUserCumulate(Date beginDate, Date endDate) throws WxErrorException {
    String url = "https://api.weixin.qq.com/datacube/getusercumulate";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("list"),
            new TypeToken<List<WxMpUserCumulate>>() {
            }.getType());
  }
}
