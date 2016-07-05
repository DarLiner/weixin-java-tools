package me.chanjar.weixin.mp.api.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.api.ApiTestModule.WxXmlMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
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

  @Inject
  protected WxMpServiceImpl wxService;

  public void testKfList() throws WxErrorException {
    WxMpKfList kfList = this.wxService.getKefuService().kfList();
    Assert.assertNotNull(kfList);
    System.err.println(kfList);
  }

  public void testKfOnlineList() throws WxErrorException {
    WxMpKfOnlineList kfOnlineList = this.wxService.getKefuService()
        .kfOnlineList();
    Assert.assertNotNull(kfOnlineList);
    System.err.println(kfOnlineList);
  }

  @DataProvider
  public Object[][] getKfAccount() {
    WxXmlMpInMemoryConfigStorage configStorage = (WxXmlMpInMemoryConfigStorage) this.wxService
        .getWxMpConfigStorage();
    return new Object[][] { { configStorage.getKfAccount() } };
  }

  @Test(dataProvider = "getKfAccount")
  public void testKfAccountAdd(String kfAccount) throws WxErrorException {
    WxMpKfAccountRequest request = WxMpKfAccountRequest.builder()
        .kfAccount(kfAccount).nickName("我晕").rawPassword("123").build();
    Assert.assertTrue(this.wxService.getKefuService().kfAccountAdd(request));
  }

  @Test(dependsOnMethods = {
      "testKfAccountAdd" }, dataProvider = "getKfAccount")
  public void testKfAccountUpdate(String kfAccount) throws WxErrorException {
    WxMpKfAccountRequest request = WxMpKfAccountRequest.builder()
        .kfAccount(kfAccount).nickName("我晕").rawPassword("123").build();
    Assert.assertTrue(this.wxService.getKefuService().kfAccountUpdate(request));
  }

  @Test(dependsOnMethods = {
      "testKfAccountUpdate" }, dataProvider = "getKfAccount")
  public void testKfAccountUploadHeadImg(String kfAccount)
      throws WxErrorException {
    File imgFile = new File("src\\test\\resources\\mm.jpeg");
    boolean result = this.wxService.getKefuService()
        .kfAccountUploadHeadImg(kfAccount, imgFile);
    Assert.assertTrue(result);
  }

  @Test(dependsOnMethods = {
      "testKfAccountUploadHeadImg" }, dataProvider = "getKfAccount")
  public void testKfAccountDel(String kfAccount) throws WxErrorException {
    boolean result = this.wxService.getKefuService().kfAccountDel(kfAccount);
    Assert.assertTrue(result);
  }

}
