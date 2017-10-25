package me.chanjar.weixin.mp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;

import java.io.Serializable;

@XStreamAlias("xml")
@Data
public class WxMpXmlOutVideoMessage extends WxMpXmlOutMessage {
  private static final long serialVersionUID = 1745902309380113978L;

  @XStreamAlias("Video")
  protected final Video video = new Video();

  public WxMpXmlOutVideoMessage() {
    this.msgType = WxConsts.XML_MSG_VIDEO;
  }

  @XStreamAlias("Video")
  @Data
  public static class Video implements Serializable {
    private static final long serialVersionUID = -6445448977569651183L;

    @XStreamAlias("MediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String mediaId;

    @XStreamAlias("Title")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String title;

    @XStreamAlias("Description")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String description;

  }

}
