package me.chanjar.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserTagService;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016/9/2.
 */
public class WxMpUserTagServiceImpl implements WxMpUserTagService {
  protected final Logger log = LoggerFactory.getLogger(WxMpDataCubeServiceImpl.class);
  private static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/tags";

  private WxMpService wxMpService;

  public WxMpUserTagServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxUserTag tagCreate(String name) throws WxErrorException {
    String url = API_URL_PREFIX + "/create";
    JsonObject json = new JsonObject();
    JsonObject groupJson = new JsonObject();
    groupJson.addProperty("name", name);
    json.add("tag", groupJson);

    String responseContent = this.wxMpService.execute(
            new SimplePostRequestExecutor(),
            url,
            json.toString());
    this.log.debug("\nurl:{}\nparams:{}\nresponse:{}",url, name, responseContent);
    return WxUserTag.fromJson(responseContent);
  }
}
