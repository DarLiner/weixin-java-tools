package me.chanjar.weixin.mp.api.impl;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.pay.WxRedpackResult;
import me.chanjar.weixin.mp.bean.pay.WxSendRedpackRequest;

/**
 * 测试支付相关接口
 * Created by Binary Wang on 2016/7/28.
 * @author binarywang (https://github.com/binarywang)
 */
@Test
@Guice(modules = ApiTestModule.class)
public class WxMpPayServiceImplTest {

  @Inject
  protected WxMpServiceImpl wxService;

  @Test
  public void testGetPrepayId() throws Exception {

  }

  @Test
  public void testGetPrepayId1() throws Exception {

  }

  @Test
  public void testGetJsapiPayInfo() throws Exception {

  }

  @Test
  public void testGetNativePayInfo() throws Exception {

  }

  @Test
  public void testGetPayInfo() throws Exception {

  }

  @Test
  public void testGetJSSDKPayResult() throws Exception {

  }

  @Test
  public void testGetJSSDKCallbackData() throws Exception {

  }

  @Test
  public void testRefundPay() throws Exception {

  }

  @Test
  public void testCheckJSSDKCallbackDataSignature() throws Exception {

  }

  @Test
  public void testSendRedpack() throws Exception {
    WxSendRedpackRequest request = new WxSendRedpackRequest();
    request.setActName("abc");
    request.setClientIp("aaa");
    request.setMchBillno("aaaa");
    request
        .setReOpenid(((ApiTestModule.WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid());
    WxRedpackResult redpackResult = this.wxService.getPayService().sendRedpack(request);
    System.err.println(redpackResult);
  }

}