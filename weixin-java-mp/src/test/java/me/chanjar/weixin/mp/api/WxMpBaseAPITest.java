package me.chanjar.weixin.mp.api;

import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * 基础API测试
 * @author chanjarster
 *
 */
@Test(groups = "baseAPI")
@Guice(modules = ApiTestModule.class)
public class WxMpBaseAPITest {

  @Inject
  protected WxMpServiceImpl wxService;

  public void testRefreshAccessToken() throws WxErrorException {
    WxMpConfigStorage configStorage = this.wxService.getWxMpConfigStorage();
    String before = configStorage.getAccessToken();
    this.wxService.getAccessToken(false);

    String after = configStorage.getAccessToken();
    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }

}
