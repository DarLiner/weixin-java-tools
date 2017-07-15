package me.chanjar.weixin.mp.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMemberCardService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.membercard.WxMpMemberCardActivatedMessage;
import me.chanjar.weixin.mp.bean.membercard.WxMpMemberCardUserInfoResult;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会员卡相关接口的实现类
 *
 * @author YuJian(mgcnrx11@gmail.com)
 * @version 2017/7/8
 */
public class WxMpMemberCardServiceImpl implements WxMpMemberCardService {

  private final Logger log = LoggerFactory.getLogger(WxMpMemberCardServiceImpl.class);

  private static final String MEMBER_CARD_ACTIVATE = "https://api.weixin.qq.com/card/membercard/activate";
  private static final String MEMBER_CARD_USER_INFO_GET = "https://api.weixin.qq.com/card/membercard/userinfo/get";

  private WxMpService wxMpService;

  private static final Gson GSON = new Gson();

  WxMpMemberCardServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  /**
   * 得到WxMpService
   */
  @Override
  public WxMpService getWxMpService() {
    return this.wxMpService;
  }

  /**
   * 会员卡激活接口
   *
   * @param activatedMessage 激活所需参数
   * @return 调用返回的JSON字符串。
   * @throws WxErrorException 接口调用失败抛出的异常
   */
  @Override
  public String activateMemberCard(WxMpMemberCardActivatedMessage activatedMessage) throws WxErrorException {
    return this.wxMpService.post(MEMBER_CARD_ACTIVATE, GSON.toJson(activatedMessage));
  }

  /**
   * 拉取会员信息接口
   *
   * @param cardId 会员卡的CardId，微信分配
   * @param code   领取会员的会员卡Code
   * @return 会员信息的结果对象
   * @throws WxErrorException 接口调用失败抛出的异常
   */
  @Override
  public WxMpMemberCardUserInfoResult getUserInfo(String cardId, String code) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("card_id", cardId);
    jsonObject.addProperty("code",code);

    String responseContent = this.getWxMpService().post(MEMBER_CARD_USER_INFO_GET, jsonObject.toString());
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement,
      new TypeToken<WxMpMemberCardUserInfoResult>() {
      }.getType());
  }
}
