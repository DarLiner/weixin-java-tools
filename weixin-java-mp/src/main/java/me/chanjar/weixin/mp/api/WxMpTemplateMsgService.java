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

  /**
   * <pre>
   * 获得模板ID
   * 从行业模板库选择模板到帐号后台，获得模板ID的过程可在MP中完成
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
   * 接口地址格式：https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN
   * </pre>
   *@param shortTemplateId 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
   * @return templateId 模板Id
   */
  String addTemplate(String shortTemplateId) throws WxErrorException;
}
