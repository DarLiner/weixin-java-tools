package me.chanjar.weixin.mp.api.impl;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
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
    ApiTestModule.WxXmlMpInMemoryConfigStorage configProvider = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.getWxMpConfigStorage();
    this.wxService.getUserService().userUpdateRemark(configProvider.getOpenId(), "测试备注名");
  }

  public void testUserInfo() throws WxErrorException {
    ApiTestModule.WxXmlMpInMemoryConfigStorage configProvider = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.getWxMpConfigStorage();
    WxMpUser user = this.wxService.getUserService().userInfo(configProvider.getOpenId(), null);
    Assert.assertNotNull(user);
    System.out.println(user);
  }

  public void testUserList() throws WxErrorException {
    WxMpUserList wxMpUserList = this.wxService.getUserService().userList(null);
    Assert.assertNotNull(wxMpUserList);
    Assert.assertFalse(wxMpUserList.getCount() == -1);
    Assert.assertFalse(wxMpUserList.getTotal() == -1);
    Assert.assertFalse(wxMpUserList.getOpenIds().size() == -1);
    System.out.println(wxMpUserList);
  }

  public void testGroupQueryUserGroup() throws WxErrorException {
    ApiTestModule.WxXmlMpInMemoryConfigStorage configStorage = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.getWxMpConfigStorage();
    long groupid = this.wxService.getGroupService().userGetGroup(configStorage.getOpenId());
    Assert.assertTrue(groupid != -1l);
  }

  public void testGroupMoveUser() throws WxErrorException {
    ApiTestModule.WxXmlMpInMemoryConfigStorage configStorage = (ApiTestModule.WxXmlMpInMemoryConfigStorage) wxService.getWxMpConfigStorage();
    this.wxService.getGroupService().userUpdateGroup(configStorage.getOpenId(), this.wxService.getGroupService().groupGet().get(3).getId());
  }

}
