package me.chanjar.weixin.mp.bean.datacube;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 用户增减数据接口的返回JSON数据包
 * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
 * </pre>
 */
public class WxDataCubeUserSummary implements Serializable {
  private static final long serialVersionUID = -2336654489906694173L;

  private Date refDate;

  private Integer userSource;

  private Integer newUser;

  private Integer cancelUser;

  public Date getRefDate() {
    return this.refDate;
  }

  public void setRefDate(Date refDate) {
    this.refDate = refDate;
  }

  public Integer getUserSource() {
    return this.userSource;
  }

  public void setUserSource(Integer userSource) {
    this.userSource = userSource;
  }

  public Integer getNewUser() {
    return this.newUser;
  }

  public void setNewUser(Integer newUser) {
    this.newUser = newUser;
  }

  public Integer getCancelUser() {
    return this.cancelUser;
  }

  public void setCancelUser(Integer cancelUser) {
    this.cancelUser = cancelUser;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
