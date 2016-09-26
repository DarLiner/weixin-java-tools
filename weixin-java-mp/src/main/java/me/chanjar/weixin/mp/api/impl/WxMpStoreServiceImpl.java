package me.chanjar.weixin.mp.api.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map.Entry;

import org.joor.Reflect;

import com.google.common.collect.Lists;

import me.chanjar.weixin.common.annotation.Required;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpStoreService;
import me.chanjar.weixin.mp.bean.WxMpStoreBaseInfo;

/**
 *  Created by Binary Wang on 2016/9/26.
 * @author binarywang (https://github.com/binarywang)
 *
 */
public class WxMpStoreServiceImpl implements WxMpStoreService {

  private WxMpService wxMpService;

  public WxMpStoreServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public void add(WxMpStoreBaseInfo request) throws WxErrorException {
    checkParameters(request);

    String url = "http://api.weixin.qq.com/cgi-bin/poi/addpoi";
    //    String data = "{\"business\":{\"base_info\":{\"business_name\":\"haha\",\"branch_name\":\"abc\",\"province\":\"aaa\",\"city\":\"aaa\",\"district\":\"aaa\",\"telephone\":\"122\",\"categories\":\"adsdas\",\"offset_type\":\"1\",\"longitude\":\"115.32375\",\"latitude\":\"25.097486\"}}}";
    String response = this.wxMpService.post(url, request.toJson());
    //    String response = this.wxMpService.post(url, data);
    WxError wxError = WxError.fromJson(response);
    if (wxError.getErrorCode() != 0) {
      throw new WxErrorException(wxError);
    }
  }

  private void checkParameters(WxMpStoreBaseInfo request) {
    List<String> nullFields = Lists.newArrayList();
    for (Entry<String, Reflect> entry : Reflect.on(request).fields()
        .entrySet()) {
      Reflect reflect = entry.getValue();
      try {
        Field field = request.getClass().getDeclaredField(entry.getKey());
        if (field.isAnnotationPresent(Required.class)
            && reflect.get() == null) {
          nullFields.add(entry.getKey());
        }
      } catch (NoSuchFieldException | SecurityException e) {
        e.printStackTrace();
      }
    }

    if (!nullFields.isEmpty()) {
      throw new IllegalArgumentException("必填字段[" + nullFields + "]必须提供值");
    }

  }

}
