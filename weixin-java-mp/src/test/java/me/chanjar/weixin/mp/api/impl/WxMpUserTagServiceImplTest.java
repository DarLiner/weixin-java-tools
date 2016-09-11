package me.chanjar.weixin.mp.api.impl;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;

/**
 *
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016/9/2.
 */
@Test
@Guice(modules = ApiTestModule.class)
public class WxMpUserTagServiceImplTest {
  @Inject
  protected WxMpServiceImpl wxService;

  private Integer tagId;

  @Test
  public void testTagCreate() throws Exception {
    String tagName = "测试标签" + System.currentTimeMillis();
    WxUserTag res = this.wxService.getUserTagService().tagCreate(tagName);
    System.out.println(res);
    this.tagId = res.getId();
    Assert.assertEquals(tagName, res.getName());
  }

  @Test
  public void testTagGet() throws Exception {
    List<WxUserTag> res = this.wxService.getUserTagService().tagGet();
    System.out.println(res);
    Assert.assertNotNull(res);
  }

  @Test(dependsOnMethods = { "testTagCreate" })
  public void testTagUpdate() throws Exception {
    String tagName = "修改标签" + System.currentTimeMillis();
    Boolean res = this.wxService.getUserTagService().tagUpdate(this.tagId, tagName);
    System.out.println(res);
    Assert.assertTrue(res);
  }

}