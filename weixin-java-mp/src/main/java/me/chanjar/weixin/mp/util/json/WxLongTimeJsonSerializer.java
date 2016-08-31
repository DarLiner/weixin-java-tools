package me.chanjar.weixin.mp.util.json;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Created by Binary Wang on 2016/7/13.
 */
public class WxLongTimeJsonSerializer extends JsonSerializer<Long> {
  private static SimpleDateFormat DF = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");

  @Override
  public void serialize(Long value, JsonGenerator gen,
      SerializerProvider serializers)
      throws IOException {
    gen.writeString(DF.format(value * 1000));
  }
}
