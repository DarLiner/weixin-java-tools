package me.chanjar.weixin.mp.api.impl;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.customerservice.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.customerservice.result.WxMpKfList;
import me.chanjar.weixin.mp.bean.customerservice.result.WxMpKfOnlineList;

import java.io.File;

import org.testng.Assert;

/**
 * 测试客服相关接口
 * @author Binary Wang
 *
 */
@Test
@Guice(modules = ApiTestModule.class)
public class WxMpKefuImplTest {
  private static final String KF_ACCOUNT = "kf2009@youxintest";
  
  @Inject
  protected WxMpServiceImpl wxService;

  public void testKfList() throws WxErrorException {
    WxMpKfList kfList = this.wxService.getKefuService().kfList();
    Assert.assertNotNull(kfList);
    System.err.println(kfList);
  }

  public void testKfOnlineList() throws WxErrorException {
    WxMpKfOnlineList kfOnlineList = this.wxService.getKefuService().kfOnlineList();
    Assert.assertNotNull(kfOnlineList);
    System.err.println(kfOnlineList);
  }

  public void testKfAccountAdd() throws WxErrorException {
    WxMpKfAccountRequest request = WxMpKfAccountRequest.builder()
        .kfAccount(KF_ACCOUNT).nickName("我晕").rawPassword("123").build();
    Assert.assertTrue(this.wxService.getKefuService().kfAccountAdd(request));
  }
  
  @Test(dependsOnMethods = { "testKfAccountAdd" })
  public void testKfAccountUpdate() throws WxErrorException {
    WxMpKfAccountRequest request = WxMpKfAccountRequest.builder()
        .kfAccount(KF_ACCOUNT).nickName("我晕").rawPassword("123").build();
    Assert.assertTrue(this.wxService.getKefuService().kfAccountUpdate(request));
  }
  
  @Test(dependsOnMethods = { "testKfAccountUpdate" })
  public void testKfAccountUploadHeadImg() throws WxErrorException {
    File imgFile = new File("src\\test\\resources\\mm.jpeg");
    boolean result = this.wxService.getKefuService().kfAccountUploadHeadImg(KF_ACCOUNT, imgFile);
    Assert.assertTrue(result);
  }
  
  @Test(dependsOnMethods = { "testKfAccountUploadHeadImg" })
  public void testKfAccountDel() throws WxErrorException {    
    boolean result = this.wxService.getKefuService().kfAccountDel(KF_ACCOUNT);
    Assert.assertTrue(result);
  }

}
