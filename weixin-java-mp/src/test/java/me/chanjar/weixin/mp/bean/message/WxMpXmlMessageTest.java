package me.chanjar.weixin.mp.bean.message;

import java.util.List;

import org.testng.annotations.*;

import me.chanjar.weixin.common.api.WxConsts;

import static org.testng.Assert.*;

@Test
public class WxMpXmlMessageTest {

  public void testFromXml() {

    String xml = "<xml>"
      + "<ToUserName><![CDATA[toUser]]></ToUserName>"
      + "<FromUserName><![CDATA[fromUser]]></FromUserName> "
      + "<CreateTime>1348831860</CreateTime>"
      + "<MsgType><![CDATA[text]]></MsgType>"
      + "<Content><![CDATA[this is a test]]></Content>"
      + "<MsgId>1234567890123456</MsgId>"
      + "<PicUrl><![CDATA[this is a url]]></PicUrl>"
      + "<MediaId><![CDATA[media_id]]></MediaId>"
      + "<Format><![CDATA[Format]]></Format>"
      + "<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
      + "<Location_X>23.134521</Location_X>"
      + "<Location_Y>113.358803</Location_Y>"
      + "<Scale>20</Scale>"
      + "<Label><![CDATA[位置信息]]></Label>"
      + "<Description><![CDATA[公众平台官网链接]]></Description>"
      + "<Url><![CDATA[url]]></Url>"
      + "<Title><![CDATA[公众平台官网链接]]></Title>"
      + "<Event><![CDATA[subscribe]]></Event>"
      + "<EventKey><![CDATA[qrscene_123123]]></EventKey>"
      + "<Ticket><![CDATA[TICKET]]></Ticket>"
      + "<Latitude>23.137466</Latitude>"
      + "<Longitude>113.352425</Longitude>"
      + "<Precision>119.385040</Precision>"
      + "<ScanCodeInfo>"
      + " <ScanType><![CDATA[qrcode]]></ScanType>"
      + " <ScanResult><![CDATA[1]]></ScanResult>"
      + "</ScanCodeInfo>"
      + "<SendPicsInfo>"
      + " <Count>1</Count>\n"
      + " <PicList>"
      + "  <item>"
      + "   <PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>"
      + "  </item>"
      + " </PicList>"
      + "</SendPicsInfo>"
      + "<SendLocationInfo>"
      + "  <Location_X><![CDATA[23]]></Location_X>\n"
      + "  <Location_Y><![CDATA[113]]></Location_Y>\n"
      + "  <Scale><![CDATA[15]]></Scale>\n"
      + "  <Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>\n"
      + "  <Poiname><![CDATA[wo de poi]]></Poiname>\n"
      + "</SendLocationInfo>"
      + "</xml>";
    WxMpXmlMessage wxMessage = WxMpXmlMessage.fromXml(xml);
    assertEquals(wxMessage.getToUser(), "toUser");
    assertEquals(wxMessage.getFromUser(), "fromUser");
    assertEquals(wxMessage.getCreateTime(), new Long(1348831860L));
    assertEquals(wxMessage.getMsgType(), WxConsts.XmlMsgType.TEXT);
    assertEquals(wxMessage.getContent(), "this is a test");
    assertEquals(wxMessage.getMsgId(), new Long(1234567890123456L));
    assertEquals(wxMessage.getPicUrl(), "this is a url");
    assertEquals(wxMessage.getMediaId(), "media_id");
    assertEquals(wxMessage.getFormat(), "Format");
    assertEquals(wxMessage.getThumbMediaId(), "thumb_media_id");
    assertEquals(wxMessage.getLocationX(), 23.134521d);
    assertEquals(wxMessage.getLocationY(), 113.358803d);
    assertEquals(wxMessage.getScale(), 20d);
    assertEquals(wxMessage.getLabel(), "位置信息");
    assertEquals(wxMessage.getDescription(), "公众平台官网链接");
    assertEquals(wxMessage.getUrl(), "url");
    assertEquals(wxMessage.getTitle(), "公众平台官网链接");
    assertEquals(wxMessage.getEvent(), "subscribe");
    assertEquals(wxMessage.getEventKey(), "qrscene_123123");
    assertEquals(wxMessage.getTicket(), "TICKET");
    assertEquals(wxMessage.getLatitude(), 23.137466);
    assertEquals(wxMessage.getLongitude(), 113.352425);
    assertEquals(wxMessage.getPrecision(), 119.385040);
    assertEquals(wxMessage.getScanCodeInfo().getScanType(), "qrcode");
    assertEquals(wxMessage.getScanCodeInfo().getScanResult(), "1");
    assertEquals(wxMessage.getSendPicsInfo().getCount(), new Long(1L));
    assertEquals(wxMessage.getSendPicsInfo().getPicList().get(0).getPicMd5Sum(), "1b5f7c23b5bf75682a53e7b6d163e185");
    assertEquals(wxMessage.getSendLocationInfo().getLocationX(), "23");
    assertEquals(wxMessage.getSendLocationInfo().getLocationY(), "113");
    assertEquals(wxMessage.getSendLocationInfo().getScale(), "15");
    assertEquals(wxMessage.getSendLocationInfo().getLabel(), " 广州市海珠区客村艺苑路 106号");
    assertEquals(wxMessage.getSendLocationInfo().getPoiName(), "wo de poi");
  }

  public void testFromXml2() {

    String xml = "<xml>"
      + "<ToUserName><![CDATA[toUser]]></ToUserName>"
      + "<FromUserName><![CDATA[fromUser]]></FromUserName> "
      + "<CreateTime>1348831860</CreateTime>"
      + "<MsgType><![CDATA[text]]></MsgType>"
      + "<Content><![CDATA[this is a test]]></Content>"
      + "<MsgID>1234567890123456</MsgID>"
      + "<PicUrl><![CDATA[this is a url]]></PicUrl>"
      + "<MediaId><![CDATA[media_id]]></MediaId>"
      + "<Format><![CDATA[Format]]></Format>"
      + "<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
      + "<Location_X>23.134521</Location_X>"
      + "<Location_Y>113.358803</Location_Y>"
      + "<Scale>20</Scale>"
      + "<Label><![CDATA[位置信息]]></Label>"
      + "<Description><![CDATA[公众平台官网链接]]></Description>"
      + "<Url><![CDATA[url]]></Url>"
      + "<Title><![CDATA[公众平台官网链接]]></Title>"
      + "<Event><![CDATA[subscribe]]></Event>"
      + "<EventKey><![CDATA[qrscene_123123]]></EventKey>"
      + "<Ticket><![CDATA[TICKET]]></Ticket>"
      + "<Latitude>23.137466</Latitude>"
      + "<Longitude>113.352425</Longitude>"
      + "<Precision>119.385040</Precision>"
      + "<ScanCodeInfo>"
      + " <ScanType><![CDATA[qrcode]]></ScanType>"
      + " <ScanResult><![CDATA[1]]></ScanResult>"
      + "</ScanCodeInfo>"
      + "<SendPicsInfo>"
      + " <Count>1</Count>\n"
      + " <PicList>"
      + "  <item>"
      + "   <PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>"
      + "  </item>"
      + "  <item>"
      + "   <PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>"
      + "  </item>"
      + " </PicList>"
      + "</SendPicsInfo>"
      + "<SendLocationInfo>"
      + "  <Location_X><![CDATA[23]]></Location_X>\n"
      + "  <Location_Y><![CDATA[113]]></Location_Y>\n"
      + "  <Scale><![CDATA[15]]></Scale>\n"
      + "  <Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>\n"
      + "  <Poiname><![CDATA[wo de poi]]></Poiname>\n"
      + "</SendLocationInfo>"
      + "</xml>";
    WxMpXmlMessage wxMessage = WxMpXmlMessage.fromXml(xml);
    assertEquals(wxMessage.getToUser(), "toUser");
    assertEquals(wxMessage.getFromUser(), "fromUser");
    assertEquals(wxMessage.getCreateTime(), new Long(1348831860L));
    assertEquals(wxMessage.getMsgType(), WxConsts.XmlMsgType.TEXT);
    assertEquals(wxMessage.getContent(), "this is a test");
    assertEquals(wxMessage.getMsgId(), new Long(1234567890123456L));
    assertEquals(wxMessage.getPicUrl(), "this is a url");
    assertEquals(wxMessage.getMediaId(), "media_id");
    assertEquals(wxMessage.getFormat(), "Format");
    assertEquals(wxMessage.getThumbMediaId(), "thumb_media_id");
    assertEquals(wxMessage.getLocationX(), 23.134521d);
    assertEquals(wxMessage.getLocationY(), 113.358803d);
    assertEquals(wxMessage.getScale(), 20d);
    assertEquals(wxMessage.getLabel(), "位置信息");
    assertEquals(wxMessage.getDescription(), "公众平台官网链接");
    assertEquals(wxMessage.getUrl(), "url");
    assertEquals(wxMessage.getTitle(), "公众平台官网链接");
    assertEquals(wxMessage.getEvent(), "subscribe");
    assertEquals(wxMessage.getEventKey(), "qrscene_123123");
    assertEquals(wxMessage.getTicket(), "TICKET");
    assertEquals(wxMessage.getLatitude(), 23.137466);
    assertEquals(wxMessage.getLongitude(), 113.352425);
    assertEquals(wxMessage.getPrecision(), 119.385040);
    assertEquals(wxMessage.getScanCodeInfo().getScanType(), "qrcode");
    assertEquals(wxMessage.getScanCodeInfo().getScanResult(), "1");
    assertEquals(wxMessage.getSendPicsInfo().getCount(), new Long(1L));
    assertEquals(wxMessage.getSendPicsInfo().getPicList().get(0).getPicMd5Sum(), "1b5f7c23b5bf75682a53e7b6d163e185");
    assertEquals(wxMessage.getSendLocationInfo().getLocationX(), "23");
    assertEquals(wxMessage.getSendLocationInfo().getLocationY(), "113");
    assertEquals(wxMessage.getSendLocationInfo().getScale(), "15");
    assertEquals(wxMessage.getSendLocationInfo().getLabel(), " 广州市海珠区客村艺苑路 106号");
    assertEquals(wxMessage.getSendLocationInfo().getPoiName(), "wo de poi");
  }

  public void testFromXml_MASSSENDJOBFINISH() {
    //xml样例来自 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
    String xml = "<xml>\n" +
      "<ToUserName><![CDATA[gh_4d00ed8d6399]]></ToUserName>\n" +
      "<FromUserName><![CDATA[oV5CrjpxgaGXNHIQigzNlgLTnwic]]></FromUserName>\n" +
      "<CreateTime>1481013459</CreateTime>\n" +
      "<MsgType><![CDATA[event]]></MsgType>\n" +
      "<Event><![CDATA[MASSSENDJOBFINISH]]></Event>\n" +
      "<MsgID>1000001625</MsgID>\n" +
      "<Status><![CDATA[err(30003)]]></Status>\n" +
      "<TotalCount>0</TotalCount>\n" +
      "<FilterCount>0</FilterCount>\n" +
      "<SentCount>0</SentCount>\n" +
      "<ErrorCount>0</ErrorCount>\n" +
      "<CopyrightCheckResult>\n" +
      "<Count>2</Count>\n" +
      "<ResultList>\n" +
      "<item>\n" +
      "<ArticleIdx>1</ArticleIdx>\n" +
      "<UserDeclareState>0</UserDeclareState>\n" +
      "<AuditState>2</AuditState>\n" +
      "<OriginalArticleUrl><![CDATA[Url_1]]></OriginalArticleUrl>\n" +
      "<OriginalArticleType>1</OriginalArticleType>\n" +
      "<CanReprint>1</CanReprint>\n" +
      "<NeedReplaceContent>1</NeedReplaceContent>\n" +
      "<NeedShowReprintSource>1</NeedShowReprintSource>\n" +
      "</item>\n" +
      "<item>\n" +
      "<ArticleIdx>2</ArticleIdx>\n" +
      "<UserDeclareState>0</UserDeclareState>\n" +
      "<AuditState>2</AuditState>\n" +
      "<OriginalArticleUrl><![CDATA[Url_2]]></OriginalArticleUrl>\n" +
      "<OriginalArticleType>1</OriginalArticleType>\n" +
      "<CanReprint>1</CanReprint>\n" +
      "<NeedReplaceContent>1</NeedReplaceContent>\n" +
      "<NeedShowReprintSource>1</NeedShowReprintSource>\n" +
      "</item>\n" +
      "</ResultList>\n" +
      "<CheckState>2</CheckState>\n" +
      "</CopyrightCheckResult>\n" +
      "</xml>";
    WxMpXmlMessage wxMessage = WxMpXmlMessage.fromXml(xml);
    assertEquals(wxMessage.getToUser(), "gh_4d00ed8d6399");
    assertEquals(wxMessage.getFromUser(), "oV5CrjpxgaGXNHIQigzNlgLTnwic");
    assertEquals(wxMessage.getCreateTime(), new Long(1481013459));
    assertEquals(wxMessage.getMsgType(), WxConsts.XmlMsgType.EVENT);
    assertEquals(wxMessage.getEvent(), "MASSSENDJOBFINISH");
    assertEquals(wxMessage.getMsgId(), new Long(1000001625L));
    assertEquals(wxMessage.getStatus(), "err(30003)");
    assertEquals(wxMessage.getTotalCount(), new Integer(0));
    assertEquals(wxMessage.getFilterCount(), new Integer(0));
    assertEquals(wxMessage.getSentCount(), new Integer(0));
    assertEquals(wxMessage.getErrorCount(), new Integer(0));

    CopyrightCheckResult copyrightCheckResult = wxMessage.getCopyrightCheckResult();
    List<CopyrightCheckResult.ResultItem> resultList = copyrightCheckResult.getResultList();

    assertEquals(copyrightCheckResult.getCount(), new Integer(2));
    assertEquals(copyrightCheckResult.getCheckState(), new Integer(2));

    assertEquals(resultList.get(0).getArticleIdx(), new Integer(1));
    assertEquals(resultList.get(0).getUserDeclareState(), new Integer(0));
    assertEquals(resultList.get(0).getAuditState(), new Integer(2));
    assertEquals(resultList.get(0).getOriginalArticleUrl(), "Url_1");
    assertEquals(resultList.get(0).getOriginalArticleType(), new Integer(1));
    assertEquals(resultList.get(0).getCanReprint(), new Integer(1));
    assertEquals(resultList.get(0).getNeedReplaceContent(), new Integer(1));
    assertEquals(resultList.get(0).getNeedShowReprintSource(), new Integer(1));

    assertEquals(resultList.get(1).getArticleIdx(), new Integer(2));
    assertEquals(resultList.get(1).getUserDeclareState(), new Integer(0));
    assertEquals(resultList.get(1).getAuditState(), new Integer(2));
    assertEquals(resultList.get(1).getOriginalArticleUrl(), "Url_2");
    assertEquals(resultList.get(1).getOriginalArticleType(), new Integer(1));
    assertEquals(resultList.get(1).getCanReprint(), new Integer(1));
    assertEquals(resultList.get(1).getNeedReplaceContent(), new Integer(1));
    assertEquals(resultList.get(1).getNeedShowReprintSource(), new Integer(1));
  }

}
