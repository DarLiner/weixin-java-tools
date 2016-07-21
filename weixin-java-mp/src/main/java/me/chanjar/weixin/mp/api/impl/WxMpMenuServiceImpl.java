package me.chanjar.weixin.mp.api.impl;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * Created by Binary Wang on 2016/7/21.
 */
public class WxMpMenuServiceImpl implements WxMpMenuService {

  private WxMpService wxMpService;

  public WxMpMenuServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }

  @Override
  public void menuCreate(WxMenu menu) throws WxErrorException {
    if (menu.getMatchRule() != null) {
      String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
      this.wxMpService.execute(new SimplePostRequestExecutor(), url, menu.toJson());
    } else {
      String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
      this.wxMpService.execute(new SimplePostRequestExecutor(), url, menu.toJson());
    }
  }

  @Override
  public void menuDelete() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/delete";
    this.wxMpService.execute(new SimpleGetRequestExecutor(), url, null);
  }

  @Override
  public void menuDelete(String menuid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional";
    this.wxMpService.execute(new SimpleGetRequestExecutor(), url, "menuid=" + menuid);
  }

  @Override
  public WxMenu menuGet() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/get";
    try {
      String resultContent = this.wxMpService.execute(new SimpleGetRequestExecutor(), url, null);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据
      if (e.getError().getErrorCode() == 46003) {
        return null;
      }
      throw e;
    }
  }

  @Override
  public WxMenu menuTryMatch(String userid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch";
    try {
      String resultContent = this.wxMpService.execute(new SimpleGetRequestExecutor(), url, "user_id=" + userid);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据     46002 不存在的菜单版本
      if (e.getError().getErrorCode() == 46003 || e.getError().getErrorCode() == 46002) {
        return null;
      }
      throw e;
    }
  }
}
