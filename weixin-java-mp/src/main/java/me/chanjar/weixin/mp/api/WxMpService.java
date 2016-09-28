package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.mp.bean.WxMpIndustry;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;
import me.chanjar.weixin.mp.bean.WxMpMassVideo;
import me.chanjar.weixin.mp.bean.WxMpSemanticQuery;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpSemanticQueryResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信API的Service
 */
public interface WxMpService {

  /**
   * <pre>
   * 验证推送过来的消息的正确性
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=验证消息真实性
   * </pre>
   */
  boolean checkSignature(String timestamp, String nonce, String signature);

  /**
   * 获取access_token, 不强制刷新access_token
   *
   * @see #getAccessToken(boolean)
   */
  String getAccessToken() throws WxErrorException;

  /**
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   *
   * 另：本service的所有方法都会在access_token过期是调用此方法
   *
   * 程序员在非必要情况下尽量不要主动调用此方法
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * </pre>
   *
   * @param forceRefresh 强制刷新
   */
  String getAccessToken(boolean forceRefresh) throws WxErrorException;

  /**
   * 获得jsapi_ticket,不强制刷新jsapi_ticket
   *
   * @see #getJsapiTicket(boolean)
   */
  String getJsapiTicket() throws WxErrorException;

  /**
   * <pre>
   * 获得jsapi_ticket
   * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
   *
   * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   *
   * @param forceRefresh 强制刷新
   */
  String getJsapiTicket(boolean forceRefresh) throws WxErrorException;

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   */
  WxJsapiSignature createJsapiSignature(String url) throws WxErrorException;

  /**
   * <pre>
   * 上传群发用的图文消息，上传后才能群发图文消息
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   *
   * @see #massGroupMessageSend(me.chanjar.weixin.mp.bean.WxMpMassTagMessage)
   * @see #massOpenIdsMessageSend(me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage)
   */
  WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException;

  /**
   * <pre>
   * 上传群发用的视频，上传后才能群发视频消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   *
   * @see #massGroupMessageSend(me.chanjar.weixin.mp.bean.WxMpMassTagMessage)
   * @see #massOpenIdsMessageSend(me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage)
   */
  WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException;

  /**
   * <pre>
   * 分组群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(me.chanjar.weixin.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(me.chanjar.weixin.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   */
  WxMpMassSendResult massGroupMessageSend(WxMpMassTagMessage message) throws WxErrorException;

  /**
   * <pre>
   * 按openId列表群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(me.chanjar.weixin.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(me.chanjar.weixin.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   */
  WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException;

  /**
   * <pre>
   * 长链接转短链接接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=长链接转短链接接口
   * </pre>
   *
   */
  String shortUrl(String long_url) throws WxErrorException;

  /**
   * <pre>
   * 发送模板消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=模板消息接口
   * </pre>
   *
   * @return msgid
   */
  String templateSend(WxMpTemplateMessage templateMessage) throws WxErrorException;

  /**
   * <pre>
   * 语义查询接口
   * 详情请见：http://mp.weixin.qq.com/wiki/index.php?title=语义理解
   * </pre>
   */
  WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery) throws WxErrorException;

  /**
   * <pre>
   * 构造第三方使用网站应用授权登录的url
   * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN">网站应用微信登录开发指南</a>
   * URL格式为：https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
   * </pre>
   *
   * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
   * @param scope 应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
   * @param state 非必填，用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
   * @return url
   */
  String buildQrConnectUrl(String redirectURI, String scope, String state);

  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   *
   * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
   * @return url
   */
  String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state);

  /**
   * <pre>
   * 用code换取oauth2的access token
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   */
  WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException;

  /**
   * <pre>
   * 刷新oauth2的access token
   * </pre>
   */
  WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException;

  /**
   * <pre>
   * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以
   * </pre>
   *
   * @param lang              zh_CN, zh_TW, en
   */
  WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException;

  /**
   * <pre>
   * 验证oauth2的access token是否有效
   * </pre>
   *
   */
  boolean oauth2validateAccessToken(WxMpOAuth2AccessToken oAuth2AccessToken);

  /**
   * <pre>
   * 获取微信服务器IP地址
   * http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html
   * </pre>
   */
  String[] getCallbackIP() throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   */
  String get(String url, String queryParam) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   */
  String post(String url, String postData) throws WxErrorException;

  /**
   * <pre>
   * Service没有实现某个API的时候，可以用这个，
   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
   * 可以参考，{@link me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor}的实现方法
   * </pre>
   */
  <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException;

  /**
   * 注入 {@link WxMpConfigStorage} 的实现
   */
  void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   */
  void setRetrySleepMillis(int retrySleepMillis);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，最大重试次数
   * 默认：5次
   * </pre>
   */
  void setMaxRetryTimes(int maxRetryTimes);

  /**
   * <pre>
   * 预览接口
   * 详情请见：http://mp.weixin.qq.com/wiki/15/40b6865b893947b764e2de8e4a1fb55f.html#.E9.A2.84.E8.A7.88.E6.8E.A5.E5.8F.A3.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91
   * </pre>
   *
   * @return wxMpMassSendResult
   */
  WxMpMassSendResult massMessagePreview(WxMpMassPreviewMessage wxMpMassPreviewMessage) throws Exception;

  /**
   * <pre>
   * 设置所属行业
   * 官方文档中暂未告知响应内容
   * 详情请见：http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E8.AE.BE.E7.BD.AE.E6.89.80.E5.B1.9E.E8.A1.8C.E4.B8.9A
   * </pre>
   *
   * @return JsonObject
   */
  String setIndustry(WxMpIndustry wxMpIndustry) throws WxErrorException;

  /***
   * <pre>
   * 获取设置的行业信息
   * 详情请见：http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E8.AE.BE.E7.BD.AE.E6.89.80.E5.B1.9E.E8.A1.8C.E4.B8.9A
   * </pre>
   *
   * @return wxMpIndustry
   */
  WxMpIndustry getIndustry() throws WxErrorException;

  /**
   * 获取WxMpConfigStorage 对象
   *
   * @return WxMpConfigStorage
   */
  WxMpConfigStorage getWxMpConfigStorage();

  /**
   * 返回客服接口方法实现类，以方便调用个其各种接口
   *
   * @return WxMpKefuService
   */
  WxMpKefuService getKefuService();

  /**
   * 返回素材相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpMaterialService
   */
  WxMpMaterialService getMaterialService();

  /**
   * 返回菜单相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpMenuService
   */
  WxMpMenuService getMenuService();

  /**
   * 返回用户相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpUserService
   */
  WxMpUserService getUserService();

  /**
   * 返回用户标签相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpUserTagService
   */
  WxMpUserTagService getUserTagService();

  /**
   * 返回二维码相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpQrcodeService
   */
  WxMpQrcodeService getQrcodeService();

  /**
   * 返回卡券相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpCardService
   */
  WxMpCardService getCardService();

  /**
   * 返回微信支付相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpPayService
   */
  WxMpPayService getPayService();

  /**
   * 返回数据分析统计相关接口方法的实现类对象，以方便调用个其各种接口
   *
   * @return WxMpDataCubeService
   */
  WxMpDataCubeService getDataCubeService();

  /**
   * 返回用户黑名单管理相关接口方法的实现类对象，以方便调用其各种接口
   *
   * @return WxMpUserBlacklistService
   */
  WxMpUserBlacklistService getBlackListService();

  /**
   * 返回门店管理相关接口方法的实现类对象，以方便调用其各种接口
   *
   * @return WxMpStoreService
   */
  WxMpStoreService getStoreService();
}
