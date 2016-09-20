package me.chanjar.weixin.mp.util.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.mp.bean.Industry;
import me.chanjar.weixin.mp.bean.WxMpIndustry;

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

  private static Industry convertFromJson(JsonObject json) {
    Industry industry = new Industry();
    industry.setFirstClass(GsonHelper.getString(json, "first_class"));
    industry.setSecondClass(GsonHelper.getString(json, "second_class"));
    return industry;
  }
}
