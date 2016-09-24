package me.chanjar.weixin.mp.bean.pay;

import java.lang.reflect.Field;
import java.util.Map.Entry;

import org.joor.Reflect;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Test
public class WxSendRedpackRequestTest {

  public void test() throws NoSuchFieldException, SecurityException {

    WxSendRedpackRequest request = new WxSendRedpackRequest();
    request.setMchBillno("123");
    request.setActName("ab");
    for (Entry<String, Reflect> entry : Reflect.on(request).fields().entrySet()) {
      Reflect reflect = entry.getValue();
      if (reflect.get() == null) {
        continue;
      }

      Field field = WxSendRedpackRequest.class.getDeclaredField(entry.getKey());
      if (field.isAnnotationPresent(XStreamAlias.class)) {
        System.err.println(reflect.get() + "  =  " + field.getAnnotation(XStreamAlias.class).value());
      }
    }

  }

}
