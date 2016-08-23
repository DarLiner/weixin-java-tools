package me.chanjar.weixin.mp.api.impl;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpDataCubeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUserCumulate;
import me.chanjar.weixin.mp.bean.result.WxMpUserSummary;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 *  Created by Binary Wang on 2016/8/23.
 * @author binarywang (https://github.com/binarywang)
 */
public class WxMpDataCubeServiceImpl implements WxMpDataCubeService {
  private static final String API_URL_PREFIX = "https://api.weixin.qq.com/datacube";
  private WxMpService wxMpService;

  public WxMpDataCubeServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public List<WxMpUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusersummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxMpUserSummary>>() {
            }.getType());
  }

  @Override
  public List<WxMpUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusercumulate";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxMpUserCumulate>>() {
            }.getType());
  }
}
