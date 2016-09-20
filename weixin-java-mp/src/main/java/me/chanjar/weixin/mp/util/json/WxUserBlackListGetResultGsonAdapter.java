package me.chanjar.weixin.mp.util.json;

import com.google.gson.*;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.mp.bean.result.WxMpUserBlackListGetResult;

import java.lang.reflect.Type;

/**
 * @author miller
 */
public class WxUserBlackListGetResultGsonAdapter implements JsonDeserializer<WxMpUserBlackListGetResult> {
  @Override
  public WxMpUserBlackListGetResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    WxMpUserBlackListGetResult wxMpUserBlackListGetResult = new WxMpUserBlackListGetResult();
    wxMpUserBlackListGetResult.setTotal(GsonHelper.getInteger(o, "total"));
    wxMpUserBlackListGetResult.setCount(GsonHelper.getInteger(o, "count"));
    wxMpUserBlackListGetResult.setNextOpenId(GsonHelper.getString(o, "next_openid"));
    if (o.get("data") != null && !o.get("data").isJsonNull() && !o.get("data").getAsJsonObject().get("openid").isJsonNull()) {
      JsonArray data = o.get("data").getAsJsonObject().get("openid").getAsJsonArray();
      for (int i = 0; i < data.size(); i++) {
        wxMpUserBlackListGetResult.getOpenIds().add(GsonHelper.getAsString(data.get(i)));
      }
    }
    return wxMpUserBlackListGetResult;
  }
}
