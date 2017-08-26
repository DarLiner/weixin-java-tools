package me.chanjar.weixin.mp.bean.store;

import com.google.gson.annotations.SerializedName;
import me.chanjar.weixin.common.util.ToStringUtils;

import java.io.Serializable;

public class WxMpStoreInfo implements Serializable{
  private static final long serialVersionUID = 7300598931768355461L;

  @SerializedName("base_info")
  private WxMpStoreBaseInfo baseInfo;

  @Override
  public String toString() {
    return ToStringUtils.toSimpleString(this);
  }

  public WxMpStoreBaseInfo getBaseInfo() {
    return this.baseInfo;
  }

  public void setBaseInfo(WxMpStoreBaseInfo baseInfo) {
    this.baseInfo = baseInfo;
  }

}
