package me.chanjar.weixin.mp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserTagService;
import me.chanjar.weixin.mp.bean.tag.WxTagListUser;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    if (wxError.getErrorCode() == 0) {
      return true;
    }

    throw new WxErrorException(wxError);
  }

  @Override
  public Boolean tagDelete(Integer id) throws WxErrorException {
    String url = API_URL_PREFIX + "/delete";

    JsonObject json = new JsonObject();
    JsonObject tagJson = new JsonObject();
    tagJson.addProperty("id", id);
    json.add("tag", tagJson);

    String responseContent = this.wxMpService.post(url, json.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}", url, json.toString(),
        responseContent);
    WxError wxError = WxError.fromJson(responseContent);
    if (wxError.getErrorCode() == 0) {
      return true;
    }

    throw new WxErrorException(wxError);
  }

  @Override
  public WxTagListUser tagListUser(Integer tagId, String nextOpenid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get";

    JsonObject json = new JsonObject();
    json.addProperty("tagid", tagId);
    json.addProperty("next_openid", StringUtils.trimToEmpty(nextOpenid));

    String responseContent = this.wxMpService.post(url, json.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}", url, json.toString(),
      responseContent);
    return WxTagListUser.fromJson(responseContent);
  }

  @Override
  public boolean batchTagging(Integer tagId, String[] openids) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";

    JsonObject json = new JsonObject();
    json.addProperty("tagid", tagId);
    JsonArray openidArrayJson = new JsonArray();
    for (String openid : openids) {
      openidArrayJson.add(openid);
    }
    json.add("openid_list", openidArrayJson);

    String responseContent = this.wxMpService.post(url, json.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}", url, json.toString(),
      responseContent);
    WxError wxError = WxError.fromJson(responseContent);
    if (wxError.getErrorCode() == 0) {
      return true;
    }

    throw new WxErrorException(wxError);
  }

  @Override
  public boolean batchUntagging(Integer tagId, String[] openids) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";

    JsonObject json = new JsonObject();
    json.addProperty("tagid", tagId);
    JsonArray openidArrayJson = new JsonArray();
    for (String openid : openids) {
      openidArrayJson.add(openid);
    }
    json.add("openid_list", openidArrayJson);

    String responseContent = this.wxMpService.post(url, json.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}", url, json.toString(),
      responseContent);
    WxError wxError = WxError.fromJson(responseContent);
    if (wxError.getErrorCode() == 0) {
      return true;
    }

    throw new WxErrorException(wxError);
  }
}
