package me.chanjar.weixin.mp.bean.datacube;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 统计接口的共用属性类
 * @author binarywang(https://github.com/binarywang)
 *         Created by Binary Wang on 2016/8/25.
 */
public class WxDataCubeBaseResult {
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  /**
   * ref_date
   * 数据的日期，需在begin_date和end_date之间
   */
  @SerializedName("ref_date")
  private String refDate;

  public String getRefDate() {
    return this.refDate;
  }

  public void setRefDate(String refDate) {
    this.refDate = refDate;
  }
  
}
