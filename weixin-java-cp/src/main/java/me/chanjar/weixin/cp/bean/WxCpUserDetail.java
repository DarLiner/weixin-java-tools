package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * <pre>
 *  使用user_ticket获取成员详情接口返回类.
 *  Created by BinaryWang on 2018/4/22.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public class WxCpUserDetail {
  @SerializedName("userid")
  private String userId;
  private String name;
  private String mobile;
  private String gender;
  private String email;
  @SerializedName("qrCode")
  private String qrCode;
}
