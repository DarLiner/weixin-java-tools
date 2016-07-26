package me.chanjar.weixin.mp.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.chanjar.weixin.mp.util.json.WxLongTimeJsonSerializer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.util.crypto.WxMpCryptUtil;
import me.chanjar.weixin.mp.util.xml.XStreamTransformer;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/index.php?title=接收普通消息
 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * http://mp.weixin.qq.com/wiki/index.php?title=接收语音识别结果
 * </pre>
 *
 * @author chanjarster
 */
@XStreamAlias("xml")
public class WxMpXmlMessage implements Serializable {

  private static final long serialVersionUID = -3586245291677274914L;

  ///////////////////////
  // 以下都是微信推送过来的消息的xml的element所对应的属性
  ///////////////////////

  @XStreamAlias("ToUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String toUserName;

  @XStreamAlias("FromUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String fromUserName;

  @XStreamAlias("CreateTime")
  @JsonSerialize(using = WxLongTimeJsonSerializer.class)
  private Long createTime;

  @XStreamAlias("MsgType")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String msgType;

  @XStreamAlias("Content")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String content;

  @XStreamAlias("MsgId")
  private Long msgId;

  @XStreamAlias("PicUrl")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String picUrl;

  @XStreamAlias("MediaId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String mediaId;

  @XStreamAlias("Format")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String format;

  @XStreamAlias("ThumbMediaId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String thumbMediaId;

  @XStreamAlias("Location_X")
  private Double locationX;

  @XStreamAlias("Location_Y")
  private Double locationY;

  @XStreamAlias("Scale")
  private Double scale;

  @XStreamAlias("Label")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String label;

  @XStreamAlias("Title")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String title;

  @XStreamAlias("Description")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String description;

  @XStreamAlias("Url")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String url;

  @XStreamAlias("Event")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String event;

  @XStreamAlias("EventKey")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String eventKey;

  @XStreamAlias("Ticket")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String ticket;

  @XStreamAlias("Latitude")
  private Double latitude;

  @XStreamAlias("Longitude")
  private Double longitude;

  @XStreamAlias("Precision")
  private Double precision;

  @XStreamAlias("Recognition")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String recognition;

  ///////////////////////////////////////
  // 群发消息返回的结果
  ///////////////////////////////////////
  /**
   * 群发的结果
   */
  @XStreamAlias("Status")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String status;
  /**
   * group_id下粉丝数；或者openid_list中的粉丝数
   */
  @XStreamAlias("TotalCount")
  private Integer totalCount;
  /**
   * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，filterCount = sentCount + errorCount
   */
  @XStreamAlias("FilterCount")
  private Integer filterCount;
  /**
   * 发送成功的粉丝数
   */
  @XStreamAlias("SentCount")
  private Integer sentCount;
  /**
   * 发送失败的粉丝数
   */
  @XStreamAlias("ErrorCount")
  private Integer errorCount;

  ///////////////////////////////////////
  // 客服会话管理相关事件推送
  ///////////////////////////////////////
  /**
   * 创建或关闭客服会话时的客服帐号
   */
  @XStreamAlias("KfAccount")
  private String kfAccount;
  /**
   * 转接客服会话时的转入客服帐号
   */
  @XStreamAlias("ToKfAccount")
  private String toKfAccount;
  /**
   * 转接客服会话时的转出客服帐号
   */
  @XStreamAlias("FromKfAccount")
  private String fromKfAccount;

  ///////////////////////////////////////
  // 卡券相关事件推送
  ///////////////////////////////////////
  @XStreamAlias("CardId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String cardId;

  @XStreamAlias("FriendUserName")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String friendUserName;

  @XStreamAlias("IsGiveByFriend")
  private Integer isGiveByFriend; // 是否为转赠，1代表是，0代表否

  @XStreamAlias("UserCardCode")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String userCardCode;

  @XStreamAlias("OldUserCardCode")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String oldUserCardCode;

  @XStreamAlias("OuterId")
  private Integer outerId;

  @XStreamAlias("ScanCodeInfo")
  private ScanCodeInfo scanCodeInfo = new ScanCodeInfo();

  @XStreamAlias("SendPicsInfo")
  private SendPicsInfo sendPicsInfo = new SendPicsInfo();

  @XStreamAlias("SendLocationInfo")
  private SendLocationInfo sendLocationInfo = new SendLocationInfo();

  public String getToUserName() {
    return this.toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public Long getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  /**
   * <pre>
   * 当接受用户消息时，可能会获得以下值：
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LOCATION}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LINK}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_EVENT}
   * </pre>
   *
   */
  public String getMsgType() {
    return this.msgType;
  }

  /**
   * <pre>
   * 当发送消息的时候使用：
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_NEWS}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_MUSIC}
   * </pre>
   *
   * @param msgType
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getMsgId() {
    return this.msgId;
  }

  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }

  public String getPicUrl() {
    return this.picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getThumbMediaId() {
    return this.thumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }

  public Double getLocationX() {
    return this.locationX;
  }

  public void setLocationX(Double locationX) {
    this.locationX = locationX;
  }

  public Double getLocationY() {
    return this.locationY;
  }

  public void setLocationY(Double locationY) {
    this.locationY = locationY;
  }

  public Double getScale() {
    return this.scale;
  }

  public void setScale(Double scale) {
    this.scale = scale;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getEvent() {
    return this.event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getEventKey() {
    return this.eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public String getTicket() {
    return this.ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public Double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getPrecision() {
    return this.precision;
  }

  public void setPrecision(Double precision) {
    this.precision = precision;
  }

  public String getRecognition() {
    return this.recognition;
  }

  public void setRecognition(String recognition) {
    this.recognition = recognition;
  }

  public String getFromUserName() {
    return this.fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  public static WxMpXmlMessage fromXml(String xml) {
    return XStreamTransformer.fromXml(WxMpXmlMessage.class, xml);
  }

  public static WxMpXmlMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxMpXmlMessage.class, is);
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml
   * @param wxMpConfigStorage
   * @param timestamp
   * @param nonce
   * @param msgSignature
   */
  public static WxMpXmlMessage fromEncryptedXml(String encryptedXml,
      WxMpConfigStorage wxMpConfigStorage, String timestamp, String nonce,
      String msgSignature) {
    WxMpCryptUtil cryptUtil = new WxMpCryptUtil(wxMpConfigStorage);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce,
        encryptedXml);
    return fromXml(plainText);
  }

  public static WxMpXmlMessage fromEncryptedXml(InputStream is,
      WxMpConfigStorage wxMpConfigStorage, String timestamp, String nonce,
      String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxMpConfigStorage,
          timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getFilterCount() {
    return this.filterCount;
  }

  public void setFilterCount(Integer filterCount) {
    this.filterCount = filterCount;
  }

  public Integer getSentCount() {
    return this.sentCount;
  }

  public void setSentCount(Integer sentCount) {
    this.sentCount = sentCount;
  }

  public Integer getErrorCount() {
    return this.errorCount;
  }

  public void setErrorCount(Integer errorCount) {
    this.errorCount = errorCount;
  }

  public String getCardId() {
    return this.cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getFriendUserName() {
    return this.friendUserName;
  }

  public void setFriendUserName(String friendUserName) {
    this.friendUserName = friendUserName;
  }

  public Integer getIsGiveByFriend() {
    return this.isGiveByFriend;
  }

  public void setIsGiveByFriend(Integer isGiveByFriend) {
    this.isGiveByFriend = isGiveByFriend;
  }

  public String getUserCardCode() {
    return this.userCardCode;
  }

  public void setUserCardCode(String userCardCode) {
    this.userCardCode = userCardCode;
  }

  public String getOldUserCardCode() {
    return this.oldUserCardCode;
  }

  public void setOldUserCardCode(String oldUserCardCode) {
    this.oldUserCardCode = oldUserCardCode;
  }

  public Integer getOuterId() {
    return this.outerId;
  }

  public void setOuterId(Integer outerId) {
    this.outerId = outerId;
  }

  public WxMpXmlMessage.ScanCodeInfo getScanCodeInfo() {
    return this.scanCodeInfo;
  }

  public void setScanCodeInfo(WxMpXmlMessage.ScanCodeInfo scanCodeInfo) {
    this.scanCodeInfo = scanCodeInfo;
  }

  public WxMpXmlMessage.SendPicsInfo getSendPicsInfo() {
    return this.sendPicsInfo;
  }

  public void setSendPicsInfo(WxMpXmlMessage.SendPicsInfo sendPicsInfo) {
    this.sendPicsInfo = sendPicsInfo;
  }

  public WxMpXmlMessage.SendLocationInfo getSendLocationInfo() {
    return this.sendLocationInfo;
  }

  public void setSendLocationInfo(
      WxMpXmlMessage.SendLocationInfo sendLocationInfo) {
    this.sendLocationInfo = sendLocationInfo;
  }

  public String getKfAccount() {
    return this.kfAccount;
  }

  public void setKfAccount(String kfAccount) {
    this.kfAccount = kfAccount;
  }

  public String getToKfAccount() {
    return this.toKfAccount;
  }

  public void setToKfAccount(String toKfAccount) {
    this.toKfAccount = toKfAccount;
  }

  public String getFromKfAccount() {
    return this.fromKfAccount;
  }

  public void setFromKfAccount(String fromKfAccount) {
    this.fromKfAccount = fromKfAccount;
  }

  @XStreamAlias("ScanCodeInfo")
  public static class ScanCodeInfo {
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @XStreamAlias("ScanType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanType;

    @XStreamAlias("ScanResult")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanResult;

    /**
     * 扫描类型，一般是qrcode
     */
    public String getScanType() {

      return this.scanType;
    }

    public void setScanType(String scanType) {
      this.scanType = scanType;
    }

    /**
     * 扫描结果，即二维码对应的字符串信息
     */
    public String getScanResult() {
      return this.scanResult;
    }

    public void setScanResult(String scanResult) {
      this.scanResult = scanResult;
    }

  }

  @XStreamAlias("SendPicsInfo")
  public static class SendPicsInfo {
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @XStreamAlias("Count")
    private Long count;

    @XStreamAlias("PicList")
    protected final List<Item> picList = new ArrayList<>();

    public Long getCount() {
      return this.count;
    }

    public void setCount(Long count) {
      this.count = count;
    }

    public List<Item> getPicList() {
      return this.picList;
    }

    @XStreamAlias("item")
    public static class Item {
      @Override
      public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.JSON_STYLE);
      }

      @XStreamAlias("PicMd5Sum")
      @XStreamConverter(value = XStreamCDataConverter.class)
      private String picMd5Sum;

      public String getPicMd5Sum() {
        return this.picMd5Sum;
      }

      public void setPicMd5Sum(String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
      }
    }
  }

  @XStreamAlias("SendLocationInfo")
  public static class SendLocationInfo {

    @XStreamAlias("Location_X")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String locationX;

    @XStreamAlias("Location_Y")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String locationY;

    @XStreamAlias("Scale")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scale;

    @XStreamAlias("Label")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String label;

    @XStreamAlias("Poiname")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String poiname;

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String getLocationX() {
      return this.locationX;
    }

    public void setLocationX(String locationX) {
      this.locationX = locationX;
    }

    public String getLocationY() {
      return this.locationY;
    }

    public void setLocationY(String locationY) {
      this.locationY = locationY;
    }

    public String getScale() {
      return this.scale;
    }

    public void setScale(String scale) {
      this.scale = scale;
    }

    public String getLabel() {
      return this.label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getPoiname() {
      return this.poiname;
    }

    public void setPoiname(String poiname) {
      this.poiname = poiname;
    }
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
