package me.chanjar.weixin.mp.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 门店基础信息
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016-09-23.
 */
public class WxMpStoreBaseInfo {
  public static class WxMpStorePhoto {
    /**
     * 照片url
     */
    @SerializedName("photo_url")
    private String photoUrl;
  }

  /**
  * sid
  * 商户自己的id，用于后续审核通过收到poi_id 的通知时，做对应关系。请商户自己保证唯一识别性
  */
  @SerializedName("sid")
  private String sid;

  /**
  *  business_name
  * 门店名称（仅为商户名，如：国美、麦当劳，不应包含地区、地址、分店名等信息，错误示例：北京国美）
  * 不能为空，15个汉字或30个英文字符内
  */
  @SerializedName("business_name")
  private String businessName;

  /**
  * branch_name
  * 分店名称（不应包含地区信息，不应与门店名有重复，错误示例：北京王府井店）
  * 10个字以内
  */
  @SerializedName("branch_name")
  private String branch_name;

  /**
  * province
  * 门店所在的省份（直辖市填城市名,如：北京市）
  * 10个字以内
  */
  @SerializedName("province")
  private String province;

  /**
  * city
  * 门店所在的城市
  * 10个字以内
  */
  @SerializedName("city")
  private String city;

  /**
  * district
  * 门店所在地区
  * 10个字以内
  */
  @SerializedName("district")
  private String district;

  /**
  * address
  * 门店所在的详细街道地址（不要填写省市信息）
  * （东莞等没有“区”行政区划的城市，该字段可不必填写。其余城市必填。）
  */
  @SerializedName("address")
  private String address;

  /**
  *  telephone
  * 门店的电话（纯数字，区号、分机号均由“-”隔开）
  */
  @SerializedName("telephone")
  private String telephone;

  /**
  * categories
  * 门店的类型（不同级分类用“,”隔开，如：美食，川菜，火锅。详细分类参见附件：微信门店类目表）
  */
  @SerializedName("categories")
  private String categories;

  /**
  * offset_type
  * 坐标类型，1 为火星坐标（目前只能选1）
  */
  @SerializedName("offset_type")
  private String offset_type;

  /**
  * longitude
  * 门店所在地理位置的经度
  */
  @SerializedName("longitude")
  private String longitude;

  /**
  * latitude
  * 门店所在地理位置的纬度（经纬度均为火星坐标，最好选用腾讯地图标记的坐标）
  */
  @SerializedName("latitude")
  private String latitude;

  /**
  * photo_list
  * 图片列表，url 形式，可以有多张图片，尺寸为 640*340px。必须为上一接口生成的url。
  * 图片内容不允许与门店不相关，不允许为二维码、员工合照（或模特肖像）、营业执照、无门店正门的街景、地图截图、公交地铁站牌、菜单截图等
  */
  @SerializedName("photo_list")
  private List<WxMpStorePhoto> photo_list;

  /**
  * recommend
  * 推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容
  * 200字以内
  */
  @SerializedName("recommend")
  private String recommend;

  /**
  * special
  * 特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
  */
  @SerializedName("special")
  private String special;

  /**
  * introduction
  * 商户简介，主要介绍商户信息等
  * 300字以内
  */
  @SerializedName("introduction")
  private String introduction;

  /**
   * open_time
  * 营业时间，24 小时制表示，用“-”连接，如  8:00-20:00
  */
  @SerializedName("open_time")
  private String open_time;

  /**
  * avg_price
  * 人均价格，大于0 的整数
  */
  @SerializedName("avg_price")
  private Integer avg_price;
}
