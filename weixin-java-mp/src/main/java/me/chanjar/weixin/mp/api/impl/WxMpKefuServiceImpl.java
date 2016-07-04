package me.chanjar.weixin.mp.api.impl;

import java.io.File;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.customerservice.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.customerservice.result.WxMpKfList;
import me.chanjar.weixin.mp.bean.customerservice.result.WxMpKfOnlineList;

/**
 * 
 * @author Binary Wang
 *
 */
public class WxMpKefuServiceImpl implements WxMpKefuService {
  private WxMpService wxMpService;

  @Override
  public void setWxMpService(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxMpKfList kfList() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
    String responseContent = this.wxMpService.execute(
          new SimpleGetRequestExecutor(), url, null);
    return WxMpKfList.fromJson(responseContent);
  }

  @Override
  public WxMpKfOnlineList kfOnlineList() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist";
    String responseContent = this.wxMpService.execute(
          new SimpleGetRequestExecutor(), url, null);
    return WxMpKfOnlineList.fromJson(responseContent);
  }

  @Override
  public boolean kfAccountAdd(WxMpKfAccountRequest request) throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/add";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url, request.toJson());
    return true;
  }

  @Override
  public boolean kfAccountUpdate(WxMpKfAccountRequest request) throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/update";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url, request.toJson());
    return true;
  }

  @Override
  public boolean kfAccountUploadHeadImg(String kfAccount, File imgFile) throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?kf_account=" + kfAccount ;
    this.wxMpService.execute(new MediaUploadRequestExecutor(), url, imgFile);
    return true;
  }

  @Override
  public boolean kfAccountDel(String kfAccount) throws WxErrorException {
    String url = "https://api.weixin.qq.com/customservice/kfaccount/del?kf_account=" + kfAccount;
    this.wxMpService.execute(new SimpleGetRequestExecutor(), url, null);
    return true;
  }

}
