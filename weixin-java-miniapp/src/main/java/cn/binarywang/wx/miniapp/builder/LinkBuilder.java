package cn.binarywang.wx.miniapp.builder;

import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;

/**
 * 图文链接builder
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class LinkBuilder extends BaseBuilder<LinkBuilder> {
  private String title;
  private String description;
  private String url;
  private String thumbUrl;

  public LinkBuilder() {
    this.msgType = WxMaConstants.KefuMsgType.IMAGE;
  }

  public LinkBuilder title(String title) {
    this.title = title;
    return this;
  }

  public LinkBuilder description(String description) {
    this.description = description;
    return this;
  }

  public LinkBuilder url(String url) {
    this.url = url;
    return this;
  }

  public LinkBuilder thumbUrl(String thumbUrl) {
    this.thumbUrl = thumbUrl;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setLink(WxMaKefuMessage.KfLink.builder().title(this.title)
      .description(this.description)
      .url(this.url)
      .thumbUrl(this.thumbUrl)
      .build()
    );
    return m;
  }
}
