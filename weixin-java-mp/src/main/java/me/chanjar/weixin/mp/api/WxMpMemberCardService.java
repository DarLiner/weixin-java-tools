package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.membercard.WxMpMemberCardActivatedMessage;
import me.chanjar.weixin.mp.bean.membercard.WxMpMemberCardUserInfoResult;

/**
 * 会员卡相关接口
 *
 * @author YuJian(mgcnrx11@gmail.com)
 * @version 2017/7/8
 */
public interface WxMpMemberCardService {

  /**
   * 得到WxMpService
   */
  WxMpService getWxMpService();

  /**
   * 会员卡激活接口
   *
   * @param activatedMessage 激活所需参数
   * @return 调用返回的JSON字符串。
   * @throws WxErrorException 接口调用失败抛出的异常
   */
  String activateMemberCard(WxMpMemberCardActivatedMessage activatedMessage) throws WxErrorException;

  /**
   * 拉取会员信息接口
   *
   * @param cardId 会员卡的CardId，微信分配
   * @param code 领取会员的会员卡Code
   * @return 会员信息的结果对象
   * @throws WxErrorException 接口调用失败抛出的异常
   */
  WxMpMemberCardUserInfoResult getUserInfo(String cardId, String code) throws WxErrorException;
}
