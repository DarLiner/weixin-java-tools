package me.chanjar.weixin.mp.api.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserTagService;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;

/**
 *
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016/9/2.
 */
public class WxMpUserTagServiceImpl implements WxMpUserTagService {
  protected final Logger log = LoggerFactory
      .getLogger(WxMpDataCubeServiceImpl.class);
  private static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/tags";

  private WxMpService wxMpService;

  public WxMpUserTagServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxUserTag tagCreate(String name) throws WxErrorException {
    String url = API_URL_PREFIX + "/create";
    JsonObject json = new JsonObject();
    JsonObject tagJson = new JsonObject();
    tagJson.addProperty("name", name);
    json.add("tag", tagJson);

    String responseContent = this.wxMpService.post(url, json.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}", url, json.toString(),
        responseContent);
    return WxUserTag.fromJson(responseContent);
  }

  @Override
  public List<WxUserTag> tagGet() throws WxErrorException {
    String url = API_URL_PREFIX + "/get";

    String responseContent = this.wxMpService.get(url, null);
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}", url, "[empty]",
        responseContent);
    return WxUserTag.listFromJson(responseContent);
  }

  @Override
  public Boolean tagUpdate(Integer id, String name) throws WxErrorException {
    String url = API_URL_PREFIX + "/update";

    JsonObject json = new JsonObject();
    JsonObject tagJson = new JsonObject();
    tagJson.addProperty("id", id);
    tagJson.addProperty("name", name);
    json.add("tag", tagJson);

    String responseContent = this.wxMpService.post(url, json.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}", url, json.toString(), responseContent);
    WxError wxError = WxError.fromJson(responseContent);
    return wxError.getErrorCode() == 0;
  }
}
