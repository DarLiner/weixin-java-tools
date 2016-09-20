package me.chanjar.weixin.mp.api.impl;

import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.result.WxMpUserBlackListGetResult;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miller
 */
@Test(groups = "userAPI")
@Guice(modules = ApiTestModule.class)
public class WxMpUserBlackListServiceImplTest {
  //此处openid只是开发的时候测试用 使用者测试的时候请替换自己公众号的openid
  private final String TEST_OPENID = "o9VAswOI0KSXFUtFHgk9Kb9Rtkys";
  @Inject
  protected WxMpServiceImpl wxService;

  @Test
  public void testBlackList() throws Exception {
    WxMpUserBlackListGetResult wxMpUserBlackListGetResult = this.wxService.getBlackListService().blackList(TEST_OPENID);
    Assert.assertNotNull(wxMpUserBlackListGetResult);
    Assert.assertFalse(wxMpUserBlackListGetResult.getCount() == -1);
    Assert.assertFalse(wxMpUserBlackListGetResult.getTotal() == -1);
    Assert.assertFalse(wxMpUserBlackListGetResult.getOpenIds().size() == -1);
    System.out.println(wxMpUserBlackListGetResult);
  }

  @Test
  public void testPushToBlackList() throws Exception {
    List<String> openIdList = new ArrayList<>();
    openIdList.add(TEST_OPENID);
    this.wxService.getBlackListService().pushToBlackList(openIdList);
  }

  @Test
  public void testPullFromBlackList() throws Exception {
    List<String> openIdList = new ArrayList<>();
    openIdList.add(TEST_OPENID);
    this.wxService.getBlackListService().pullFromBlackList(openIdList);
  }

}
