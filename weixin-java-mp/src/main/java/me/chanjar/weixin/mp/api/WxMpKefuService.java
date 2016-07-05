package me.chanjar.weixin.mp.api;

import java.io.File;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.customerservice.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.customerservice.result.WxMpKfList;
import me.chanjar.weixin.mp.bean.customerservice.result.WxMpKfOnlineList;

/**
 * 客服接口 ，
 * 命名采用kefu拼音的原因是：
 * 其英文CustomerService如果再加上Service后缀显得有点啰嗦，
 * 如果不加又显得表意不完整
 * @author Binary Wang
 *
 */
public interface WxMpKefuService {  
  /**
   * <pre>
   * 获取客服基本信息
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN
   * </pre>
   */
  WxMpKfList kfList() throws WxErrorException;
  
  /**
   * <pre>
   * 获取在线客服接待信息
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=ACCESS_TOKEN
   * </pre>
   */
  WxMpKfOnlineList kfOnlineList() throws WxErrorException;
  
  /**
   * <pre>
   * 添加客服账号
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean kfAccountAdd(WxMpKfAccountRequest request) throws WxErrorException;
  
  /**
   * <pre>
   * 设置客服信息（更新）
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean kfAccountUpdate(WxMpKfAccountRequest request) throws WxErrorException;
  
  /**
   * <pre>
   * 上传客服头像
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
   * </pre>
   */
  
  boolean kfAccountUploadHeadImg(String kfAccount, File imgFile) throws WxErrorException;
  
  /**
   * <pre>
   * 删除客服账号
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki/18/749901f4e123170fb8a4d447ae6040ba.html">客服管理</a>
   * https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
   * </pre>
   */
  boolean kfAccountDel(String kfAccount) throws WxErrorException;
}
