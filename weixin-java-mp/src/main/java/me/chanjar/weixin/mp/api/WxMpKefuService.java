package me.chanjar.weixin.mp.api;

import java.io.File;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfOnlineList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfSessionGetResult;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfSessionList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfSessionWaitCaseList;

/**
 * 客服接口 ，
 * 命名采用kefu拼音的原因是：
 * 其英文CustomerService如果再加上Service后缀显得有点啰嗦，
 * 如果不加又显得表意不完整
 * @author Binary Wang
 *
 */
public interface WxMpKefuService {

  //*******************客服管理接口***********************//
  /**
   * <pre>
   * 获取客服基本信息
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * 接口url格式：https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN
   * </pre>
   */
  WxMpKfList kfList() throws WxErrorException;

  /**
   * <pre>
   * 获取在线客服接待信息
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * 接口url格式：https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=ACCESS_TOKEN
   * </pre>
   */
  WxMpKfOnlineList kfOnlineList() throws WxErrorException;

  /**
   * <pre>
   * 添加客服账号
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean kfAccountAdd(WxMpKfAccountRequest request) throws WxErrorException;

  /**
   * <pre>
   * 设置客服信息（更新）
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean kfAccountUpdate(WxMpKfAccountRequest request) throws WxErrorException;

  /**
   * <pre>
   * 上传客服头像
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * 接口url格式：http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
   * </pre>
   */
  boolean kfAccountUploadHeadImg(String kfAccount, File imgFile)
      throws WxErrorException;

  /**
   * <pre>
   * 删除客服账号
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
   * </pre>
   */
  boolean kfAccountDel(String kfAccount) throws WxErrorException;

  //*******************多客服会话控制接口***********************//
  /**
   * <pre>
   * 创建会话
   * 开发者可以使用本接口，为多客服的客服工号创建会话，将某个客户直接指定给客服工号接待，需要注意此接口不会受客服自动接入数以及自动接入开关限制。只能为在线的客服（PC客户端在线，或者已绑定多客服助手）创建会话。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/4/4b256cfb246b22ad020e07cf8a61a738.html">多客服会话控制接口</a>
   * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/create?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean kfSessionCreate(String openid, String kfAccount, String text) throws WxErrorException;

  /**
   * <pre>
   * 关闭会话
   * 开发者可以使用本接口，关闭一个会话。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/4/4b256cfb246b22ad020e07cf8a61a738.html">多客服会话控制接口</a>
   * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/close?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean kfSessionClose(String openid, String kfAccount, String text) throws WxErrorException;

  /**
   * <pre>
   * 获取客户的会话状态
   * 开发者可以通过本接口获取客户当前的会话状态。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/4/4b256cfb246b22ad020e07cf8a61a738.html">多客服会话控制接口</a>
   * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/getsession?access_token=ACCESS_TOKEN&openid=OPENID
   * </pre>
   */
  WxMpKfSessionGetResult kfSessionGet(String openid) throws WxErrorException;

  /**
   * <pre>
   * 获取客服的会话列表
   * 开发者可以通过本接口获取某个客服正在接待的会话列表。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/4/4b256cfb246b22ad020e07cf8a61a738.html">多客服会话控制接口</a>
   * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/getsessionlist?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
   * </pre>
   */
  WxMpKfSessionList kfSessionList(String kfAccount) throws WxErrorException;

  /**
   * <pre>
   * 获取未接入会话列表
   * 开发者可以通过本接口获取当前正在等待队列中的会话列表，此接口最多返回最早进入队列的100个未接入会话。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/4/4b256cfb246b22ad020e07cf8a61a738.html">多客服会话控制接口</a>
   * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/getwaitcase?access_token=ACCESS_TOKEN
   * </pre>
   */
  WxMpKfSessionWaitCaseList kfSessionGetWaitCase() throws WxErrorException;
}
