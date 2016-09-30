package me.chanjar.weixin.mp.bean.custombuilder;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;

/**
 * 卡券消息builder
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.WXCARD().cardId(...).toUser(...).build();
 * </pre>
 * @author mgcnrx11
 *
 */
public final class WxCardBuilder extends BaseBuilder<WxCardBuilder> {
  private String cardId;

  public WxCardBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_WXCARD;
  }

  public WxCardBuilder cardId(String cardId) {
    this.cardId = cardId;
    return this;
  }

  @Override
  public WxMpCustomMessage build() {
    WxMpCustomMessage m = super.build();
    m.setCardId(this.cardId);
    return m;
  }
}
