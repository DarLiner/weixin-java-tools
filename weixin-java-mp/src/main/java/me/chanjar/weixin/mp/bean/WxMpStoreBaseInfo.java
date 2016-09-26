package me.chanjar.weixin.mp.bean;

/**
 * 门店基础信息
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016-09-23.
 */
public class WxStoreBaseInfo {
 /** sid
  商户自己的id，用于后续审核通过收到poi_id 的通知时，做对应关系。请商户自己保证唯一识别性
    否
  business_name
  门店名称（仅为商户名，如：国美、麦当劳，不应包含地区、地址、分店名等信息，错误示例：北京国美）
  不能为空，15个汉字或30个英文字符内
    是
  branch_name
  分店名称（不应包含地区信息，不应与门店名有重复，错误示例：北京王府井店）
    10个字以内
    是
  province
  门店所在的省份（直辖市填城市名,如：北京市）
    10个字以内
    是
  city
    门店所在的城市
10个字以内
    是
  district
    门店所在地区
10个字以内
    是
  address
  门店所在的详细街道地址（不要填写省市信息）
  是
（东莞等没有“区”行政区划的城市，该字段可不必填写。其余城市必填。）
  telephone
  门店的电话（纯数字，区号、分机号均由“-”隔开）
  是
    categories
  门店的类型（不同级分类用“,”隔开，如：美食，川菜，火锅。详细分类参见附件：微信门店类目表）
  是
    offset_type
  坐标类型，1 为火星坐标（目前只能选1）
  是
    longitude
  门店所在地理位置的经度
    是
  latitude
  门店所在地理位置的纬度（经纬度均为火星坐标，最好选用腾讯地图标记的坐标）
  是
  */
}
