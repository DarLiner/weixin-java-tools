package me.chanjar.weixin.mp.api.impl;

import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxXmlMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.pay.request.WxEntPayRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayRefundRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPaySendRedpackRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayUnifiedOrderRequest;
import me.chanjar.weixin.mp.bean.pay.result.WxPayRefundResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPaySendRedpackResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPayUnifiedOrderResult;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.File;

/**
 * 测试支付相关接口
 * Created by Binary Wang on 2016/7/28.
 * @author binarywang (https://github.com/binarywang)
 */
@Test
@Guice(modules = ApiTestModule.class)
public class WxMpPayServiceImplTest {

  @Inject
  protected WxMpService wxService;

  @Test
  public void testGetPayInfo() throws Exception {

  }

  @Test
  public void testRefund() throws Exception {
    WxPayRefundRequest request = new WxPayRefundRequest();
    request.setOutRefundNo("aaa");
    request.setOutTradeNo("1111");
    request.setTotalFee(1222);
    request.setRefundFee(111);
    File keyFile = new File("E:\\dlt.p12");
    WxPayRefundResult result = this.wxService.getPayService().refund(request, keyFile);
    System.err.println(result);
  }

  @Test
  public void testCheckJSSDKCallbackDataSignature() throws Exception {

  }

  @Test
  public void testSendRedpack() throws Exception {
    WxPaySendRedpackRequest request = new WxPaySendRedpackRequest();
    request.setActName("abc");
    request.setClientIp("aaa");
    request.setMchBillno("aaaa");
    request
        .setReOpenid(((WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid());
    File keyFile = new File("E:\\dlt.p12");
    WxPaySendRedpackResult redpackResult = this.wxService.getPayService().sendRedpack(request, keyFile);
    System.err.println(redpackResult);
  }

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpPayServiceImpl#unifiedOrder(WxPayUnifiedOrderRequest)}.
   */
  @Test
  public void testUnifiedOrder() throws WxErrorException {
    WxPayUnifiedOrderResult result = this.wxService.getPayService()
        .unifiedOrder(WxPayUnifiedOrderRequest.builder().body("1111111")
            .totalFee(1).spbillCreateIp("111111").notifyURL("111111")
            .tradeType("JSAPI").openid("122").outTradeNo("111111").build());
    System.err.println(result);
  }

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpPayServiceImpl#queryOrder(String, String)} .
   */
  @Test
  public final void testQueryOrder() throws WxErrorException {
    //System.err.println(this.wxService.getPayService().queryOrder(null, null));
    System.err.println(this.wxService.getPayService().queryOrder("11212121", null));
    System.err.println(this.wxService.getPayService().queryOrder(null, "11111"));
  }

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpPayServiceImpl#closeOrder(String)} .
   */
  @Test
  public final void testCloseOrder() throws WxErrorException {
    System.err.println(this.wxService.getPayService().closeOrder("11212121"));
  }

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpPayServiceImpl#entPay(WxEntPayRequest, File)}.
   */
  @Test
  public final void testEntPay() throws WxErrorException {
    File keyFile = new File("E:\\dlt.p12");
    WxEntPayRequest request = new WxEntPayRequest();
    System.err.println(this.wxService.getPayService().entPay(request, keyFile));
  }

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpPayServiceImpl#queryEntPay(String, File)}.
   */
  @Test
  public final void testQueryEntPay() throws WxErrorException {
    File keyFile = new File("E:\\dlt.p12");
    System.err.println(this.wxService.getPayService().queryEntPay("11212121", keyFile));
  }
}
