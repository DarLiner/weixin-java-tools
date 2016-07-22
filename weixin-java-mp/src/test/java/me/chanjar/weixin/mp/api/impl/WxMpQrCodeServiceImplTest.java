package me.chanjar.weixin.mp.api.impl;

import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.File;

/**
 * 测试用户相关的接口
 * 
 * @author chanjarster
 */
@Test(groups = "qrCodeAPI")
@Guice(modules = ApiTestModule.class)
public class WxMpQrCodeServiceImplTest {

  @Inject
  protected WxMpServiceImpl wxService;

  public void testQrCodeCreateTmpTicket() throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateTmpTicket(1, null);
    Assert.assertNotNull(ticket.getUrl());
    Assert.assertNotNull(ticket.getTicket());
    Assert.assertTrue(ticket.getExpire_seconds() != -1);
    System.out.println(ticket);
  }

  public void testQrCodeCreateLastTicket() throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateLastTicket(1);
    Assert.assertNotNull(ticket.getUrl());
    Assert.assertNotNull(ticket.getTicket());
    Assert.assertTrue(ticket.getExpire_seconds() == -1);
    System.out.println(ticket);
  }

  public void testQrCodePicture() throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateLastTicket(1);
    File file = this.wxService.getQrcodeService().qrCodePicture(ticket);
    Assert.assertNotNull(file);
    System.out.println(file.getAbsolutePath());
  }
  
  public void testQrCodePictureUrl() throws WxErrorException {
    WxMpQrCodeTicket ticket = this.wxService.getQrcodeService().qrCodeCreateLastTicket(1);
    String url = this.wxService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
    Assert.assertNotNull(url);
    System.out.println(url);
  }

}
