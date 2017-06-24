package me.chanjar.weixin.cp.api.impl;

import com.google.inject.Inject;
import me.chanjar.weixin.cp.api.ApiTestModule;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * <pre>
 *  Created by BinaryWang on 2017/6/24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Guice(modules = ApiTestModule.class)
public class WxCpUserServiceImplTest {
  @Inject
  private WxCpService wxCpService;

  @Test
  public void testAuthenticate() throws Exception {
  }

  @Test
  public void testCreate() throws Exception {
    WxCpUser user = new WxCpUser();
    user.setUserId("some.woman");
    user.setName("Some Woman");
    user.setDepartIds(new Integer[]{9, 8});
    user.setEmail("none@none.com");
    user.setGender("女");
    user.setMobile("13560084979");
    user.setPosition("woman");
    user.setTelephone("3300393");
    user.addExtAttr("爱好", "table");
    this.wxCpService.getUserService().create(user);
  }

  @Test(dependsOnMethods = "testCreate")
  public void testUpdate() throws Exception {
    WxCpUser user = new WxCpUser();
    user.setUserId("some.woman");
    user.setName("Some Woman");
    user.addExtAttr("爱好", "table2");
    this.wxCpService.getUserService().update(user);
  }

  @Test
  public void testDelete() throws Exception {
    this.wxCpService.getUserService().delete("some.woman");
  }

  @Test(dependsOnMethods = "testUpdate")
  public void testGetById() throws Exception {
    WxCpUser user = this.wxCpService.getUserService().getById("some.woman");
    assertNotNull(user);
  }

  @Test
  public void testListByDepartment() throws Exception {
    List<WxCpUser> users = this.wxCpService.getUserService().listByDepartment(1, true, 0);
    assertNotEquals(users.size(), 0);
    for (WxCpUser user : users) {
      System.out.println(ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE));
    }
  }

  @Test
  public void testListSimpleByDepartment() throws Exception {
    List<WxCpUser> users = this.wxCpService.getUserService().listSimpleByDepartment(1, true, 0);
    assertNotEquals(users.size(), 0);
    for (WxCpUser user : users) {
      System.out.println(ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE));
    }
  }

}
