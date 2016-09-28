package me.chanjar.weixin.mp.api.impl;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.api.WxXmlMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.bean.tag.WxTagListUser;
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

  private Long tagId = 2L;

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

  @Test(dependsOnMethods = { "testTagCreate" })
  public void testTagDelete() throws Exception {
    Boolean res = this.wxService.getUserTagService().tagDelete(this.tagId);
    System.out.println(res);
    Assert.assertTrue(res);
  }

  @Test
  public void testTagListUser() throws Exception {
    WxTagListUser res = this.wxService.getUserTagService().tagListUser(this.tagId, null);
    System.out.println(res);
    Assert.assertNotNull(res);
  }

  @Test
  public void testBatchUntagging() throws Exception {
    String[] openids = new String[]{((WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid()};
    boolean res = this.wxService.getUserTagService().batchUntagging(this.tagId, openids);
    System.out.println(res);
    Assert.assertTrue(res);
  }

  @Test
  public void testUserTagList() throws Exception {
    List<Integer> res = this.wxService.getUserTagService().userTagList(
        ((WxXmlMpInMemoryConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid());
    System.out.println(res);
    Assert.assertNotNull(res);
  }
}
