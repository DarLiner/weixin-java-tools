package me.chanjar.weixin.mp.api.impl;

import java.io.File;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.api.WxXmlMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.pay.WxEntPayRequest;
import me.chanjar.weixin.mp.bean.pay.WxRedpackResult;
import me.chanjar.weixin.mp.bean.pay.WxSendRedpackRequest;
import me.chanjar.weixin.mp.bean.pay.WxUnifiedOrderRequest;
import me.chanjar.weixin.mp.bean.pay.WxUnifiedOrderResult;

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
        .setReOpenid(((WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid());
    WxRedpackResult redpackResult = this.wxService.getPayService().sendRedpack(request);
    System.err.println(redpackResult);
  }

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpPayServiceImpl#unifiedOrder(me.chanjar.weixin.mp.bean.pay.WxUnifiedOrderRequest)}.
   * @throws WxErrorException
   */
  @Test
  public void testUnifiedOrder() throws WxErrorException {
    WxUnifiedOrderResult result = this.wxService.getPayService()
        .unifiedOrder(WxUnifiedOrderRequest.builder().body("1111111")
            .totalFee(1).spbillCreateIp("111111").notifyURL("111111")
            .tradeType("JSAPI").openid("122").outTradeNo("111111").build());
    System.err.println(result);
  }

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpPayServiceImpl#entPay(WxEntPayRequest, File)}.
   * @throws WxErrorException
   */
  @Test
  public final void testEntPay() throws WxErrorException {
    File keyFile = new File("E:\\dlt.p12");
    WxEntPayRequest request = new WxEntPayRequest();
    System.err.println(this.wxService.getPayService().entPay(request, keyFile));
  }

}
