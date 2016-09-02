package me.chanjar.weixin.mp.api;

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
   *   创建标签
   *   一个公众号，最多可以创建100个标签。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param name 分组名字（30个字符以内）
   */
  WxUserTag tagCreate(String name) throws WxErrorException;

}
