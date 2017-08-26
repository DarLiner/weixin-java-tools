package me.chanjar.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;

/**
 * Created by keungtung on 10/12/2016.
 */

public class RespMsg extends AbstractDeviceBean {
  private static final long serialVersionUID = -4241272701707684136L;

  @SerializedName("ret_code")
  private Integer retCode;
  @SerializedName("error_info")
  private String errorInfo;

  public Integer getRetCode() {
    return retCode;
  }

  public void setRetCode(Integer retCode) {
    this.retCode = retCode;
  }

  public String getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(String errorInfo) {
    this.errorInfo = errorInfo;
  }
}
