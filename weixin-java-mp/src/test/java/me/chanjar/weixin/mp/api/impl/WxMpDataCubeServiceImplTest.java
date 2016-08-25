package me.chanjar.weixin.mp.api.impl;

import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleResult;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeArticleTotal;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

  @DataProvider
  public Object[][] oneDay() throws ParseException {
    return new Object[][]{{simpleDateFormat.parse("2016-08-22")}};
  }

  @DataProvider
  public Object[][] threeDays() throws ParseException {
    return new Object[][]{{simpleDateFormat.parse("2016-08-20"),
            simpleDateFormat.parse("2016-08-22")}};
  }

  @DataProvider
  public Object[][] sevenDays() throws ParseException {
    return new Object[][]{{simpleDateFormat.parse("2016-08-16"),
            simpleDateFormat.parse("2016-08-22")}};
  }

  @Test(dataProvider = "sevenDays")
  public void testGetUserSummary(Date beginDate, Date endDate)
      throws WxErrorException {
    List<WxDataCubeUserSummary> summaries = this.wxService.getDataCubeService()
        .getUserSummary(beginDate, endDate);
    Assert.assertNotNull(summaries);
    System.out.println(summaries);
  }

  @Test(dataProvider = "sevenDays")
  public void testGetUserCumulate(Date beginDate, Date endDate)
      throws WxErrorException {
    List<WxDataCubeUserCumulate> result = this.wxService.getDataCubeService()
        .getUserCumulate(beginDate, endDate);
    Assert.assertNotNull(result);
    System.out.println(result);
  }

  @Test(dataProvider = "oneDay")
  public void testGetArticleSummary(Date date) throws WxErrorException {
    List<WxDataCubeArticleResult> results = this.wxService.getDataCubeService()
            .getArticleSummary(date, date);
    Assert.assertNotNull(results);
    System.out.println(results);
  }

  /**
   * TODO 该接口，暂时没找到有数据的日期，无法测试验证
   */
  @Test(dataProvider = "oneDay")
  public void testGetArticleTotal(Date date) throws WxErrorException {
    List<WxDataCubeArticleTotal> results = this.wxService.getDataCubeService()
            .getArticleTotal(date, date);
    Assert.assertNotNull(results);
    System.out.println(results);
  }

  @Test(dataProvider = "threeDays")
  public void testGetUserRead(Date beginDate, Date endDate)
      throws WxErrorException {
    List<WxDataCubeArticleResult> results = this.wxService.getDataCubeService()
            .getUserRead(beginDate, endDate);
    Assert.assertNotNull(results);
    System.out.println(results);
  }

  @Test(dataProvider = "oneDay")
  public void testGetUserReadHour(Date date) throws WxErrorException {
    List<WxDataCubeArticleResult> results = this.wxService.getDataCubeService()
            .getUserReadHour(date, date);
    Assert.assertNotNull(results);
    System.out.println(results);
  }

  @Test(dataProvider = "sevenDays")
  public void testGetUserShare(Date beginDate, Date endDate)
      throws WxErrorException {
    List<WxDataCubeArticleResult> results = this.wxService.getDataCubeService()
            .getUserShare(beginDate, endDate);
    Assert.assertNotNull(results);
    System.out.println(results);
  }

  @Test(dataProvider = "oneDay")
  public void testGetUserShareHour(Date date) throws WxErrorException {
    List<WxDataCubeArticleResult> results = this.wxService.getDataCubeService()
            .getUserShareHour(date, date);
    Assert.assertNotNull(results);
    System.out.println(results);
  }
}
