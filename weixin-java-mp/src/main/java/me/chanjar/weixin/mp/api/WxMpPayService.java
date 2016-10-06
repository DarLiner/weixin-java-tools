package me.chanjar.weixin.mp.api;

import java.io.File;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.pay.WxEntPayRequest;
import me.chanjar.weixin.mp.bean.pay.WxEntPayResult;
import me.chanjar.weixin.mp.bean.pay.WxMpPayCallback;
import me.chanjar.weixin.mp.bean.pay.WxMpPayRefundResult;
import me.chanjar.weixin.mp.bean.pay.WxMpPayResult;
import me.chanjar.weixin.mp.bean.pay.WxRedpackResult;
import me.chanjar.weixin.mp.bean.pay.WxSendRedpackRequest;
import me.chanjar.weixin.mp.bean.pay.WxUnifiedOrderRequest;
import me.chanjar.weixin.mp.bean.pay.WxUnifiedOrderResult;

/**
 *  微信支付相关接口
 *  Created by Binary Wang on 2016/7/28.
 * @author binarywang (https://github.com/binarywang)
 */
public interface WxMpPayService {

  /**
   * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
   * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
   * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
   * @throws WxErrorException 
   *
   */
  WxUnifiedOrderResult unifiedOrder(WxUnifiedOrderRequest request)
      throws WxErrorException;

  /**
   * 该接口调用“统一下单”接口，并拼装发起支付请求需要的参数
   * 详见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
   *
   */
  Map<String, String> getPayInfo(WxUnifiedOrderRequest request) throws WxErrorException;


  /**
   * 该接口提供所有微信支付订单的查询,当支付通知处理异常戒丢失的情冴,商户可以通过该接口查询订单支付状态。
   * 详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
   * @throws WxErrorException 
   *
   */
  WxMpPayResult getJSSDKPayResult(String transactionId, String outTradeNo)
      throws WxErrorException;

  /**
   * 读取支付结果通知
   * 详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
   *
   */
  WxMpPayCallback getJSSDKCallbackData(String xmlData);

  /**
   * 微信支付-申请退款
   * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
   *
   * @param parameters 需要传入的退款参数的Map。以下几项为参数的必须项：<br/>
   *                   <li/> transaction_id
   *                   <li/> out_trade_no （仅在上述transaction_id为空时是必须项）
   *                   <li/> out_refund_no
   *                   <li/> total_fee
   *                   <li/> refund_fee
   * @return 退款操作结果
   */
  WxMpPayRefundResult refundPay(Map<String, String> parameters) throws WxErrorException;

  /**
   * <pre>
   * 计算Map键值对是否和签名相符,
   * 按照字段名的 ASCII 码从小到大排序(字典序)后,使用 URL 键值对的 格式(即 key1=value1&key2=value2...)拼接成字符串
   * </pre>
   *
   */
  boolean checkJSSDKCallbackDataSignature(Map<String, String> kvm, String signature);

  /**
   * 发送微信红包给个人用户
   * <pre> 
   * 文档详见:
   * 发送普通红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3
   * 发送裂变红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5&index=4
   * </pre>
   */
  WxRedpackResult sendRedpack(WxSendRedpackRequest request) throws WxErrorException;

  /**
   * <pre> 
   * 企业付款业务是基于微信支付商户平台的资金管理能力，为了协助商户方便地实现企业向个人付款，针对部分有开发能力的商户，提供通过API完成企业付款的功能。
   * 比如目前的保险行业向客户退保、给付、理赔。
   * 企业付款将使用商户的可用余额，需确保可用余额充足。查看可用余额、充值、提现请登录商户平台“资金管理”https://pay.weixin.qq.com/进行操作。
   * 注意：与商户微信支付收款资金并非同一账户，需要单独充值。
   * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
   * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers
   * @param keyFile  证书文件对象
   * </pre>
   */
  WxEntPayResult entPay(WxEntPayRequest request, File keyFile) throws WxErrorException;

}
