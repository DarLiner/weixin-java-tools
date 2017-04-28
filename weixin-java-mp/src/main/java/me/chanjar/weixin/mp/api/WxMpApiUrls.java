package me.chanjar.weixin.mp.api;

/**
 * <pre>
 * 公众号相关接口URL常量类
 * Created by Binary Wang on 2017-4-28.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public class WxMpApiUrls {
  /**
   * 获取access_token
   */
  public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

  /**
   * 获得jsapi_ticket
   */
  public static final String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";

  /**
   * 上传群发用的图文消息
   */
  public static final String MEDIA_UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";

  /**
   * 上传群发用的视频
   */
  public static final String MEDIA_UPLOAD_VIDEO_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadvideo";

  /**
   * 分组群发消息
   */
  public static final String MESSAGE_MASS_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";

  /**
   * 按openId列表群发消息
   */
  public static final String MESSAGE_MASS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send";

  /**
   * 群发消息预览接口
   */
  public static final String MESSAGE_MASS_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview";

  /**
   * 长链接转短链接接口
   */
  public static final String SHORTURL_API_URL = "https://api.weixin.qq.com/cgi-bin/shorturl";

  /**
   * 语义查询接口
   */
  public static final String SEMANTIC_SEMPROXY_SEARCH_URL = "https://api.weixin.qq.com/semantic/semproxy/search";

  /**
   * 用code换取oauth2的access token
   */
  public static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

  /**
   * 刷新oauth2的access token
   */
  public static final String OAUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

  /**
   * 用oauth2获取用户信息
   */
  public static final String OAUTH2_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";

  /**
   * 验证oauth2的access token是否有效
   */
  public static final String OAUTH2_VALIDATE_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";

  /**
   * 获取微信服务器IP地址
   */
  public static final String GET_CALLBACK_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip";

  /**
   * 第三方使用网站应用授权登录的url
   */
  public static final String QRCONNECT_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

  /**
   * oauth2授权的url连接
   */
  public static final String CONNECT_OAUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
}
