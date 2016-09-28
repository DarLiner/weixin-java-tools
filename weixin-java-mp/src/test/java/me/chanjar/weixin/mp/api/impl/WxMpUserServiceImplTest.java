 
package me.chanjar.weixin.mp.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.api.WxXmlMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.WxMpUserQuery;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

/**
 * 测试用户相关的接口
 *
 * @author chanjarster
 * @author Binary Wang
 */
@Test(groups = "userAPI")
@Guice(modules = ApiTestModule.class)
public class WxMpUserServiceImplTest {

  @Inject
  protected WxMpServiceImpl wxService;

  public void testUserUpdateRemark() throws WxErrorException {
    WxXmlMpInMemoryConfigStorage configProvider = (WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage();
    this.wxService.getUserService().userUpdateRemark(configProvider.getOpenid(), "测试备注名");
  }

  public void testUserInfo() throws WxErrorException {
    WxXmlMpInMemoryConfigStorage configProvider = (WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage();
    WxMpUser user = this.wxService.getUserService().userInfo(configProvider.getOpenid(), null);
    Assert.assertNotNull(user);
    System.out.println(user);
  }
  
  public void testUserInfoList() throws WxErrorException {
    WxXmlMpInMemoryConfigStorage configProvider = (WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage();
    List<String> openIdList = new ArrayList<>();
    openIdList.add(configProvider.getOpenid());
    List<WxMpUser> userList =	this.wxService.getUserService().userInfoList(openIdList);
    Assert.assertEquals(userList.size(), 1);
	System.out.println(userList);
  }
  
  public void testUserInfoListByWxMpUserQuery() throws WxErrorException {
    WxXmlMpInMemoryConfigStorage configProvider = (WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage();
    WxMpUserQuery query = new WxMpUserQuery();
    query.add(configProvider.getOpenid(), "zh_CN");
    List<WxMpUser> userList =	this.wxService.getUserService().userInfoList(query);
    Assert.assertEquals(userList.size(), 1);
    System.out.println(userList);
  }

  public void testUserList() throws WxErrorException {
    WxMpUserList wxMpUserList = this.wxService.getUserService().userList(null);
    Assert.assertNotNull(wxMpUserList);
    Assert.assertFalse(wxMpUserList.getCount() == -1);
    Assert.assertFalse(wxMpUserList.getTotal() == -1);
    Assert.assertFalse(wxMpUserList.getOpenIds().size() == -1);
    System.out.println(wxMpUserList);
  }
  

}
