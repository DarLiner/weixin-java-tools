package me.chanjar.weixin.mp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;

@XStreamAlias("xml")
@Data
public class WxMpXmlOutMusicMessage extends WxMpXmlOutMessage {
  private static final long serialVersionUID = -4159937804975448945L;

  @XStreamAlias("Music")
  protected final Music music = new Music();

  public WxMpXmlOutMusicMessage() {
    this.msgType = WxConsts.XML_MSG_MUSIC;
  }

  @XStreamAlias("Music")
  @Data
  public static class Music {

    @XStreamAlias("Title")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String title;

    @XStreamAlias("Description")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String description;

    @XStreamAlias("ThumbMediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String thumbMediaId;

    @XStreamAlias("MusicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String musicUrl;

    @XStreamAlias("HQMusicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String hqMusicUrl;

  }

}
