package me.chanjar.weixin.mp.api.impl;

import java.io.File;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.kefu.request.WxMpKfSessionRequest;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfOnlineList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfSessionGetResult;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfSessionList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfSessionWaitCaseList;

/**
 * 
 * @author Binary Wang
 *
 */
public class WxMpKefuServiceImpl implements WxMpKefuService {
  private WxMpService wxMpService;

  public WxMpKefuServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxMpKfList kfList() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfList.fromJson(responseContent);
  }

  @Override
  public WxMpKfOnlineList kfOnlineList() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist";
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfOnlineList.fromJson(responseContent);
  }

  @Override
  public boolean kfAccountAdd(WxMpKfAccountRequest request)
      throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/add";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public boolean kfAccountUpdate(WxMpKfAccountRequest request)
      throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/update";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public boolean kfAccountUploadHeadImg(String kfAccount, File imgFile)
      throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?kf_account="
        + kfAccount;
    this.wxMpService.execute(new MediaUploadRequestExecutor(), url, imgFile);
    return true;
  }

  @Override
  public boolean kfAccountDel(String kfAccount) throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/del?kf_account="
        + kfAccount;
    this.wxMpService.execute(new SimpleGetRequestExecutor(), url, null);
    return true;
  }

  @Override
  public boolean kfSessionCreate(String openid, String kfAccount, String text)
      throws WxErrorException {
    WxMpKfSessionRequest request = new WxMpKfSessionRequest(kfAccount, openid,
        text);
    String url = "https://api.weixin.qq.com/customservice/kfsession/create";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public boolean kfSessionClose(String openid, String kfAccount, String text)
      throws WxErrorException {
    WxMpKfSessionRequest request = new WxMpKfSessionRequest(kfAccount, openid,
        text);
    String url = "https://api.weixin.qq.com/customservice/kfsession/close";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public WxMpKfSessionGetResult kfSessionGet(String openid)
      throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfsession/getsession?openid="
        + openid;
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfSessionGetResult.fromJson(responseContent);
  }

  @Override
  public WxMpKfSessionList kfSessionList(String kfAccount)
      throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfsession/getsessionlist?kf_account="
        + kfAccount;
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfSessionList.fromJson(responseContent);
  }

  @Override
  public WxMpKfSessionWaitCaseList kfSessionGetWaitCase()
      throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfsession/getwaitcase";
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfSessionWaitCaseList.fromJson(responseContent);
  }

}
