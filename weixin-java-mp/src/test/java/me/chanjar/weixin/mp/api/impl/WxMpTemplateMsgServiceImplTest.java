package me.chanjar.weixin.mp.api.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.api.WxXmlMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.WxMpIndustry;
import me.chanjar.weixin.mp.bean.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;

/**
 * <pre>
 * Created by Binary Wang on 2016-10-14.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
@Guice(modules = ApiTestModule.class)
public class WxMpTemplateMsgServiceImplTest {
  @Inject
  protected WxMpServiceImpl wxService;

  @Test(invocationCount = 10, threadPoolSize = 10)
  public void testSendTemplateMsg() throws WxErrorException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss.SSS");
    WxXmlMpInMemoryConfigStorage configStorage = (WxXmlMpInMemoryConfigStorage) this.wxService
      .getWxMpConfigStorage();
    WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
      .toUser(configStorage.getOpenid())
      .templateId(configStorage.getTemplateId()).build();
    templateMessage.addWxMpTemplateData(
      new WxMpTemplateData("first", dateFormat.format(new Date())));
    String msgId = this.wxService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    Assert.assertNotNull(msgId);
    System.out.println(msgId);
  }

  @Test
  public void testGetIndustry() throws Exception {
    final WxMpIndustry industry = this.wxService.getTemplateMsgService().getIndustry();
    Assert.assertNotNull(industry);
    System.out.println(industry);
  }

  @Test
  public void testSetIndustry() throws Exception {
    WxMpIndustry industry = new WxMpIndustry(new WxMpIndustry.Industry("1"),
        new WxMpIndustry.Industry("04"));
    boolean result = this.wxService.getTemplateMsgService().setIndustry(industry);
    Assert.assertTrue(result);
  }

  @Test
  public void testAddTemplate() throws Exception {
    String result = this.wxService.getTemplateMsgService().addTemplate("TM00015");
    Assert.assertNotNull(result);
    System.err.println(result);
  }

}
