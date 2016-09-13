package me.chanjar.weixin.mp.api;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;

/**
 * 用户标签管理相关接口
 * Created by Binary Wang on 2016/9/2.
 * @author binarywang(https://github.com/binarywang)
 *
 */
public interface WxMpUserTagService {

  /**
   * <pre>
   * 创建标签
   * 一个公众号，最多可以创建100个标签。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param name 标签名字（30个字符以内）
   */
  WxUserTag tagCreate(String name) throws WxErrorException;

  /**
   * <pre>
   * 获取公众号已创建的标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  List<WxUserTag> tagGet() throws WxErrorException;

  /**
   * <pre>
   * 编辑标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  Boolean tagUpdate(Integer id, String name) throws WxErrorException;

  /**
   * <pre>
   * 删除标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  Boolean tagDelete(Integer id) throws WxErrorException;

}
