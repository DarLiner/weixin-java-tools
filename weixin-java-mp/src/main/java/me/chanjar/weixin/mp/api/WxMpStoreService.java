package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpStoreBaseInfo;

/**
 * 门店管理的相关接口代码
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016-09-23.
 */
public interface WxMpStoreService {
  /**
   * <pre>
   * 创建门店
   * 接口说明
   * 创建门店接口是为商户提供创建自己门店数据的接口，门店数据字段越完整，商户页面展示越丰富，越能够吸引更多用户，并提高曝光度。
   * 创建门店接口调用成功后会返回errcode 0、errmsg ok，但不会实时返回poi_id。
   * 成功创建后，会生成poi_id，但该id不一定为最终id。门店信息会经过审核，审核通过后方可获取最终poi_id，该id为门店的唯一id，强烈建议自行存储审核通过后的最终poi_id，并为后续调用使用。
   * 详情请见: <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444378120&token=&lang=zh_CN">微信门店接口</a>
   * 接口格式： http://api.weixin.qq.com/cgi-bin/poi/addpoi?access_token=TOKEN
   * </pre>
   *
   */
  void add(WxMpStoreBaseInfo request) throws WxErrorException;
}
