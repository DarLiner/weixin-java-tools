package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpIndustry;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;

/**
 * <pre>
 * 模板消息接口
 * http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
 * Created by Binary Wang on 2016-10-14.
 * @author miller.lin
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public interface WxMpTemplateMsgService {

  /**
   * <pre>
   * 设置所属行业
   * 官方文档中暂未告知响应内容
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
   * </pre>
   *
   * @return 是否成功
   */
  boolean setIndustry(WxMpIndustry wxMpIndustry) throws WxErrorException;

  /***
   * <pre>
   * 获取设置的行业信息
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
   * </pre>
   *
   * @return wxMpIndustry
   */
  WxMpIndustry getIndustry() throws WxErrorException;

  /**
   * <pre>
   * 发送模板消息
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
   * </pre>
   *
   * @return 消息Id
   */
  String sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException;

}
