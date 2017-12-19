package com.github.binarywang.wxpay.service.impl;

import com.github.binarywang.wxpay.bean.entpay.EntPayQueryRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayQueryResult;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.EntPaySerivce;
import com.github.binarywang.wxpay.service.WxPayService;

/**
 * <pre>
 *  Created by BinaryWang on 2017/12/19.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class EntPayServiceImpl implements EntPaySerivce {
  private WxPayService payService;

  public EntPayServiceImpl(WxPayService payService) {
    this.payService = payService;
  }

  @Override
  public EntPayResult entPay(EntPayRequest request) throws WxPayException {
    request.checkAndSign(this.payService.getConfig(), false);
    String url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/promotion/transfers";

    String responseContent = this.payService.post(url, request.toXML(), true);
    EntPayResult result = BaseWxPayResult.fromXML(responseContent, EntPayResult.class);
    result.checkResult(this.payService, request.getSignType(), true);
    return result;
  }

  @Override
  public EntPayQueryResult queryEntPay(String partnerTradeNo) throws WxPayException {
    EntPayQueryRequest request = new EntPayQueryRequest();
    request.setPartnerTradeNo(partnerTradeNo);
    request.checkAndSign(this.payService.getConfig(), false);

    String url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/gettransferinfo";
    String responseContent = this.payService.post(url, request.toXML(), true);
    EntPayQueryResult result = BaseWxPayResult.fromXML(responseContent, EntPayQueryResult.class);
    result.checkResult(this.payService, request.getSignType(), true);
    return result;
  }

}
