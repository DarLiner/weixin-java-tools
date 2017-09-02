package com.github.binarywang.wxpay.constant;

import java.text.SimpleDateFormat;

/**
 * <pre>
 * 微信支付常量类
 * Created by Binary Wang on 2017-8-24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxPayConstants {

  /**
   * 拉取订单评价数据接口的参数中日期格式
   */
  public static final SimpleDateFormat QUERY_COMMENT_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

  /**
   * 校验用户姓名选项，企业付款时使用
   */
  public static class CheckNameOption {
    /**
     * 不校验真实姓名
     */
    public static final String NO_CHECK = "NO_CHECK";

    /**
     * 强校验真实姓名
     */
    public static final String FORCE_CHECK = "FORCE_CHECK";
  }

  /**
   * 订单类型
   */
  public static class BillType {
    /**
     * 查询红包时使用：通过商户订单号获取红包信息
     */
    public static final String MCHT = "MCHT";

    //以下为下载对账单时的账单类型
    /**
     * 返回当日所有订单信息，默认值
     */
    public static final String ALL = "ALL";
    /**
     * 返回当日成功支付的订单
     */
    public static final String SUCCESS = "SUCCESS";
    /**
     * 返回当日退款订单
     */
    public static final String REFUND = "REFUND";
    /**
     * 返回当日充值退款订单（相比其他对账单多一栏“返还手续费”）
     */
    public static final String RECHARGE_REFUND = "RECHARGE_REFUND";
  }

  /**
   * 交易类型
   */
  public static class TradeType {
    /**
     * 原生扫码支付
     */
    public static final String NATIVE = "NATIVE";

    /**
     * App支付
     */
    public static final String APP = "APP";

    /**
     * 公众号支付
     */
    public static final String JSAPI = "JSAPI";

    /**
     * H5支付
     */
    public static final String MWEB = "MWEB";

    /**
     * 刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
     */
    public static final String MICROPAY = "MICROPAY";
  }

  /**
   * 签名类型
   */
  public static class SignType {
    public static final String HMAC_SHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";
  }


  /**
   * 限定支付方式
   */
  public static class LimitPay {
    /**
     * no_credit--指定不能使用信用卡支付
     */
    public static final String NO_CREDIT = "no_credit";
  }
}
