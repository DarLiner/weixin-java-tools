package me.chanjar.weixin.mp.bean.membercard;

import java.io.Serializable;

/**
 * Created by YuJian on 2017/7/11.
 */
public class MemberCardUserInfo implements Serializable {
  private static final long serialVersionUID = -4259196162619282129L;

  private NameValues[] commonFieldList;

  private NameValues[] customFieldList;

  public NameValues[] getCommonFieldList() {
    return commonFieldList;
  }

  public void setCommonFieldList(NameValues[] commonFieldList) {
    this.commonFieldList = commonFieldList;
  }

  public NameValues[] getCustomFieldList() {
    return customFieldList;
  }

  public void setCustomFieldList(NameValues[] customFieldList) {
    this.customFieldList = customFieldList;
  }
}
