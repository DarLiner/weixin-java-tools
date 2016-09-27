package me.chanjar.weixin.mp.api;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.store.WxMpStoreBaseInfo;
import me.chanjar.weixin.mp.bean.store.WxMpStoreInfo;

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

  /**
   * <pre>
   * 查询门店列表(指定查询起始位置和个数)
   * 商户可以通过该接口，批量查询自己名下的门店list，并获取已审核通过的poi_id（所有状态均会返回poi_id，但该poi_id不一定为最终id）、商户自身sid 用于对应、商户名、分店名、地址字段。
   * 详情请见: <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444378120&token=&lang=zh_CN">微信门店接口</a>
   * </pre>
   * @param begin 开始位置，0 即为从第一条开始查询
   * @param limit 返回数据条数，最大允许50，默认为20
   * @throws WxErrorException
   */
  List<WxMpStoreInfo> list(int begin, int limit) throws WxErrorException;

  /**
   * <pre>
   * 查询门店列表（所有）
   * 商户可以通过该接口，批量查询自己名下的门店list，并获取已审核通过的poi_id（所有状态均会返回poi_id，但该poi_id不一定为最终id）、商户自身sid 用于对应、商户名、分店名、地址字段。
   * 详情请见: <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444378120&token=&lang=zh_CN">微信门店接口</a>
   * </pre>
   * @throws WxErrorException
   */
  List<WxMpStoreInfo> listAll() throws WxErrorException;
}
