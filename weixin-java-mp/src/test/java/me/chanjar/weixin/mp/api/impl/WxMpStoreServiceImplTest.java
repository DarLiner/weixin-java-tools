package me.chanjar.weixin.mp.api.impl;

import com.google.inject.Inject;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.store.WxMpStoreBaseInfo;
import me.chanjar.weixin.mp.bean.store.WxMpStoreInfo;
import me.chanjar.weixin.mp.bean.store.WxMpStoreListResult;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author 王彬 (Binary Wang)
 *
 */
@Test
@Guice(modules = ApiTestModule.class)
public class WxMpStoreServiceImplTest {
  @Inject
  private WxMpServiceImpl wxMpService;

  /**
   * Test method for {@link me.chanjar.weixin.mp.api.impl.WxMpStoreServiceImpl#add(me.chanjar.weixin.mp.bean.store.WxMpStoreBaseInfo)}.
   * @throws WxErrorException
   */
  public void testAdd() throws WxErrorException {
    this.wxMpService.getStoreService()
        .add(WxMpStoreBaseInfo.builder().businessName("haha").branchName("abc")
            .province("aaa").district("aaa").telephone("122").address("abc").categories(new String[] { "美食,江浙菜" })
            .longitude(new BigDecimal("115.32375"))
            .latitude(new BigDecimal("25.097486")).city("aaa").offsetType(1)
            .build());
  }

  public void testGet() throws WxErrorException {
    WxMpStoreBaseInfo result = this.wxMpService.getStoreService().get("291503654");
    assertNotNull(result);
    System.err.println(result);
  }

  public void testList() throws WxErrorException {
    WxMpStoreListResult result = this.wxMpService.getStoreService().list(0, 10);
    assertNotNull(result);
    System.err.println(result);
  }

  public void testListAll() throws WxErrorException {
    List<WxMpStoreInfo> list = this.wxMpService.getStoreService().listAll();
    assertNotNull(list);
    System.err.println(list.size());
    System.err.println(list);
  }

}
