package me.chanjar.weixin.mp.api.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.result.WxMpUserCumulate;
import me.chanjar.weixin.mp.bean.result.WxMpUserSummary;

/**
 * 测试统计分析相关的接口
 *  Created by Binary Wang on 2016/8/23.
 * @author binarywang (https://github.com/binarywang)
 */
@Guice(modules = ApiTestModule.class)
public class WxMpDataCubeServiceImplTest {
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Inject
  protected WxMpServiceImpl wxService;

  @Test
  public void testGetUserSummary() throws WxErrorException, ParseException {
    Date beginDate = simpleDateFormat.parse("2016-08-20");
    Date endDate = simpleDateFormat.parse("2016-08-22");
    List<WxMpUserSummary> summaries = this.wxService.getDataCubeService()
        .getUserSummary(beginDate, endDate);
    Assert.assertNotNull(summaries);
    System.out.println(summaries);
  }

  @Test
  public void testGetUserCumulate() throws WxErrorException, ParseException {
    Date beginDate = simpleDateFormat.parse("2016-08-21");
    Date endDate = simpleDateFormat.parse("2016-08-22");
    List<WxMpUserCumulate> cumulates = this.wxService.getDataCubeService()
        .getUserCumulate(beginDate, endDate);
    Assert.assertNotNull(cumulates);
    System.out.println(cumulates);
  }

}
