package me.chanjar.weixin.mp.api.impl;

import java.io.File;
import java.util.Date;

import com.google.gson.JsonObject;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.kefu.request.WxMpKfSessionRequest;
import me.chanjar.weixin.mp.bean.kefu.result.*;

/**
 * 
 * @author Binary Wang
 *
 */
public class WxMpKefuServiceImpl implements WxMpKefuService {
  public static final String API_URL_PREFIX = "https://api.weixin.qq.com/customservice";
  private WxMpService wxMpService;

  public WxMpKefuServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public WxMpKfList kfList() throws WxErrorException {
    String url = API_URL_PREFIX + "/getkflist";
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfList.fromJson(responseContent);
  }

  @Override
  public WxMpKfOnlineList kfOnlineList() throws WxErrorException {
    String url = API_URL_PREFIX + "/getonlinekflist";
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfOnlineList.fromJson(responseContent);
  }

  @Override
  public boolean kfAccountAdd(WxMpKfAccountRequest request)
      throws WxErrorException {
    String url = API_URL_PREFIX + "/kfaccount/add";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public boolean kfAccountUpdate(WxMpKfAccountRequest request)
      throws WxErrorException {
    String url = API_URL_PREFIX + "/kfaccount/update";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public boolean kfAccountInviteWorker(WxMpKfAccountRequest request) throws WxErrorException {
    String url = API_URL_PREFIX + "/kfaccount/inviteworker";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
            request.toJson());
    return true;
  }

  @Override
  public boolean kfAccountUploadHeadImg(String kfAccount, File imgFile)
      throws WxErrorException {
    String url = API_URL_PREFIX + "/kfaccount/uploadheadimg?kf_account=" + kfAccount;
    this.wxMpService.execute(new MediaUploadRequestExecutor(), url, imgFile);
    return true;
  }

  @Override
  public boolean kfAccountDel(String kfAccount) throws WxErrorException {
    String url = API_URL_PREFIX + "/kfaccount/del?kf_account=" + kfAccount;
    this.wxMpService.execute(new SimpleGetRequestExecutor(), url, null);
    return true;
  }

  @Override
  public boolean kfSessionCreate(String openid, String kfAccount)
      throws WxErrorException {
    WxMpKfSessionRequest request = new WxMpKfSessionRequest(kfAccount, openid);
    String url = API_URL_PREFIX + "/kfsession/create";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public boolean kfSessionClose(String openid, String kfAccount)
      throws WxErrorException {
    WxMpKfSessionRequest request = new WxMpKfSessionRequest(kfAccount, openid);
    String url = API_URL_PREFIX + "/kfsession/close";
    this.wxMpService.execute(new SimplePostRequestExecutor(), url,
        request.toJson());
    return true;
  }

  @Override
  public WxMpKfSessionGetResult kfSessionGet(String openid)
      throws WxErrorException {
    String url = API_URL_PREFIX + "/kfsession/getsession?openid=" + openid;
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfSessionGetResult.fromJson(responseContent);
  }

  @Override
  public WxMpKfSessionList kfSessionList(String kfAccount)
      throws WxErrorException {
    String url = API_URL_PREFIX + "/kfsession/getsessionlist?kf_account=" + kfAccount;
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfSessionList.fromJson(responseContent);
  }

  @Override
  public WxMpKfSessionWaitCaseList kfSessionGetWaitCase()
      throws WxErrorException {
    String url = API_URL_PREFIX + "/kfsession/getwaitcase";
    String responseContent = this.wxMpService
        .execute(new SimpleGetRequestExecutor(), url, null);
    return WxMpKfSessionWaitCaseList.fromJson(responseContent);
  }

  @Override
  public WxMpKfMsgList kfMsgList(Date startTime, Date endTime, Integer msgId, Integer number) throws WxErrorException {
    if(startTime.after(endTime)){
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("起始时间不能晚于结束时间！").build());
    }

    String url = API_URL_PREFIX + "/msgrecord/getmsglist";

    JsonObject param = new JsonObject();
    param.addProperty("starttime", startTime.getTime() / 1000); //starttime	起始时间，unix时间戳
    param.addProperty("endtime", endTime.getTime() / 1000); //endtime	结束时间，unix时间戳，每次查询时段不能超过24小时
    param.addProperty("msgid", msgId); //msgid	消息id顺序从小到大，从1开始
    param.addProperty("number", number); //number	每次获取条数，最多10000条

    String responseContent = this.wxMpService.execute(new SimplePostRequestExecutor(), url, param.toString());
    return WxMpKfMsgList.fromJson(responseContent);
  }

}
