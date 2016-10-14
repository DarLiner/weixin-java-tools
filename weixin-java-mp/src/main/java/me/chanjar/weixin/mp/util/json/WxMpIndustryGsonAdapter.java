package me.chanjar.weixin.mp.util.json;

import com.google.gson.*;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.mp.bean.WxMpIndustry;

import java.lang.reflect.Type;

/**
 * @author miller
 */
public class WxMpIndustryGsonAdapter
    implements JsonSerializer<WxMpIndustry>, JsonDeserializer<WxMpIndustry> {
  @Override
  public JsonElement serialize(WxMpIndustry wxMpIndustry, Type type,
      JsonSerializationContext jsonSerializationContext) {
    JsonObject json = new JsonObject();
    json.addProperty("industry_id1", wxMpIndustry.getPrimaryIndustry().getId());
    json.addProperty("industry_id2", wxMpIndustry.getSecondIndustry().getId());
    return json;
  }

  @Override
  public WxMpIndustry deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    WxMpIndustry wxMpIndustry = new WxMpIndustry();
    JsonObject primaryIndustry = jsonElement.getAsJsonObject()
        .get("primary_industry").getAsJsonObject();
    wxMpIndustry.setPrimaryIndustry(convertFromJson(primaryIndustry));
    JsonObject secondaryIndustry = jsonElement.getAsJsonObject()
        .get("secondary_industry").getAsJsonObject();
    wxMpIndustry.setSecondIndustry(convertFromJson(secondaryIndustry));
    return wxMpIndustry;
  }

  private static WxMpIndustry.Industry convertFromJson(JsonObject json) {
    WxMpIndustry.Industry industry = new WxMpIndustry.Industry();
    industry.setFirstClass(GsonHelper.getString(json, "first_class"));
    industry.setSecondClass(GsonHelper.getString(json, "second_class"));
    return industry;
  }
}
