package cn.binarywang.wx.miniapp.bean;

import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Test
public class WxMaKefuMessageTest {

  public void testTextBuilder() {
    WxMaKefuMessage reply = WxMaKefuMessage.newTextBuilder()
      .toUser("OPENID")
      .content("sfsfdsdf")
      .build();
    assertThat(reply.toJson())
      .isEqualTo("{\"touser\":\"OPENID\",\"msgtype\":\"text\",\"text\":{\"content\":\"sfsfdsdf\"}}");
  }

  public void testImageBuilder() {
    WxMaKefuMessage reply = WxMaKefuMessage.newImageBuilder()
      .toUser("OPENID")
      .        mediaId("MEDIA_ID")
      .build();
    assertThat(reply.toJson())
      .isEqualTo( "{\"touser\":\"OPENID\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"MEDIA_ID\"}}");
  }

  public void testLinkBuilder() {
    WxMaKefuMessage reply = WxMaKefuMessage.newLinkBuilder()
      .toUser("OPENID")
      .url("url")
      .description("description")
      .title("title")
      .thumbUrl("thumbUrl")
      .build();
    assertThat(reply.toJson())
      .isEqualTo( "{\"touser\":\"OPENID\",\"msgtype\":\"image\"," +
        "\"link\":{\"title\":\"title\",\"description\":\"description\",\"url\":\"url\",\"thumb_url\":\"thumbUrl\"}}");
  }


}
