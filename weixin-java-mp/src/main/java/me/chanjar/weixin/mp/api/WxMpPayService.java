package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.*;

import java.util.Map;

/**
 *  微信支付相关接口
 *  Created by Binary Wang on 2016/7/28.
 * @author binarywang (https://github.com/binarywang)
 */
public interface WxMpPayService {


  /**
   * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
   * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
   *
   * @param openId     支付人openId
   * @param outTradeNo 商户端对应订单号
   * @param amt        金额(单位元)
   * @param body       商品描述
   * @param tradeType  交易类型 JSAPI，NATIVE，APP，WAP
   * @param ip         发起支付的客户端IP
   * @param notifyUrl  通知地址
   * @deprecated Use me.chanjar.weixin.mp.api.WxMpService.getPrepayId(Map<String, String>) instead
   */
  @Deprecated
  WxMpPrepayIdResult getPrepayId(String openId, String outTradeNo, double amt, String body, String tradeType, String ip, String notifyUrl);

  /**
   * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
   * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
   *
   * @param parameters All required/optional parameters for weixin payment
   */
  WxMpPrepayIdResult getPrepayId(Map<String, String> parameters);

  /**
   * 该接口调用“统一下单”接口，并拼装发起支付请求需要的参数
   * 详见http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E5.8F.91.E8.B5.B7.E4.B8.80.E4.B8.AA.E5.BE.AE.E4.BF.A1.E6.94.AF.E4.BB.98.E8.AF.B7.E6.B1.82
   *
   * @param parameters the required or optional parameters
   */
  Map<String, String> getPayInfo(Map<String, String> parameters) throws WxErrorException;

  /**
   * 该接口调用“统一下单”接口，并拼装NATIVE发起支付请求需要的参数
   * 详见http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E5.8F.91.E8.B5.B7.E4.B8.80.E4.B8.AA.E5.BE.AE.E4.BF.A1.E6.94.AF.E4.BB.98.E8.AF.B7.E6.B1.82
   * tradeType 交易类型 NATIVE （其他交易类型JSAPI，APP，WAP）
   *
   * @param productId  商户商品ID
   * @param outTradeNo 商户端对应订单号
   * @param amt        金额(单位元)
   * @param body       商品描述
   * @param ip         发起支付的客户端IP
   * @param notifyUrl  通知地址
   * @deprecated Use me.chanjar.weixin.mp.api.WxMpService.getPayInfo(Map<String, String>) instead
   */
  @Deprecated
  Map<String, String> getNativePayInfo(String productId, String outTradeNo, double amt, String body, String ip, String notifyUrl) throws WxErrorException;

  /**
   * 该接口调用“统一下单”接口，并拼装JSAPI发起支付请求需要的参数
   * 详见http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E5.8F.91.E8.B5.B7.E4.B8.80.E4.B8.AA.E5.BE.AE.E4.BF.A1.E6.94.AF.E4.BB.98.E8.AF.B7.E6.B1.82
   * tradeType 交易类型 JSAPI(其他交易类型NATIVE，APP，WAP)
   *
   * @param openId     支付人openId
   * @param outTradeNo 商户端对应订单号
   * @param amt        金额(单位元)
   * @param body       商品描述
   * @param ip         发起支付的客户端IP
   * @param notifyUrl  通知地址
   * @deprecated Use me.chanjar.weixin.mp.api.WxMpService.getPayInfo(Map<String, String>) instead
   */
  @Deprecated
  Map<String, String> getJsapiPayInfo(String openId, String outTradeNo, double amt, String body, String ip, String notifyUrl) throws WxErrorException;

  /**
   * 该接口提供所有微信支付订单的查询,当支付通知处理异常戒丢失的情冴,商户可以通过该接口查询订单支付状态。
   * 详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
   *
   */
  WxMpPayResult getJSSDKPayResult(String transactionId, String outTradeNo);

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
   * <p>
   * 需要传入的必填参数如下:
   * mch_billno//商户订单号
   * send_name//商户名称
   * re_openid//用户openid
   * total_amount//红包总额
   * total_num//红包发放总人数
   * wishing//红包祝福语
   * client_ip//服务器Ip地址
   * act_name//活动名称
   * remark //备注
   * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5
   * <p>
   * 使用现金红包功能需要在xml配置文件中额外设置:
   * <partnerId></partnerId>微信商户平台ID
   * <partnerKey></partnerKey>商户平台设置的API密钥
   *
   */
  WxRedpackResult sendRedpack(Map<String, String> parameters) throws WxErrorException;
}
