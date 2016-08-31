package me.chanjar.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpDataCubeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotal;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeInterfaceResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeMsgResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 *  Created by Binary Wang on 2016/8/23.
 * @author binarywang (https://github.com/binarywang)
 */
public class WxMpDataCubeServiceImpl implements WxMpDataCubeService {
  protected final Logger log = LoggerFactory.getLogger(WxMpDataCubeServiceImpl.class);

  private static final String API_URL_PREFIX = "https://api.weixin.qq.com/datacube";
  private WxMpService wxMpService;

  public WxMpDataCubeServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public List<WxDataCubeUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusersummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeUserSummary>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusercumulate";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeUserCumulate>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeArticleResult> getArticleSummary(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getarticlesummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeArticleResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeArticleTotal> getArticleTotal(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getarticletotal";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeArticleTotal>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeArticleResult> getUserRead(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getuserread";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeArticleResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeArticleResult> getUserReadHour(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getuserreadhour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeArticleResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeArticleResult> getUserShare(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusershare";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeArticleResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeArticleResult> getUserShareHour(Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusersharehour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeArticleResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsg(Date beginDate, Date endDate)
      throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsg";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeMsgResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgHour(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsghour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeMsgResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgWeek(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgweek";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeMsgResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgMonth(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgmonth";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeMsgResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgDist(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgdist";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeMsgResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgDistWeek(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgdistweek";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeMsgResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgDistMonth(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgdistmonth";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeMsgResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeInterfaceResult> getInterfaceSummary(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getinterfacesummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeInterfaceResult>>() {
            }.getType());
  }

  @Override
  public List<WxDataCubeInterfaceResult> getInterfaceSummaryHour(Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getinterfacesummaryhour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", WxMpService.SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", WxMpService.SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = this.wxMpService.post(url, param.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, param, responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(new JsonParser().parse(responseContent).getAsJsonObject().get("list"),
            new TypeToken<List<WxDataCubeInterfaceResult>>() {
            }.getType());
  }
}
