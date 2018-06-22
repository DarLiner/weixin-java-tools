package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 企业号应用信息.
 * Created by huansinho on 2018/4/13.
 * </pre>
 *
 * @author <a href="https://github.com/huansinho">huansinho</a>
 */
@Data
public class WxCpAgent implements Serializable {

  @SerializedName("errcode")
  private Integer errcode;

  @SerializedName("errmsg")
  private String errmsg;

  @SerializedName("agentid")
  private Integer agentid;

  @SerializedName("name")
  private String name;

  @SerializedName("square_logo_url")
  private String squareLogoUrl;

  @SerializedName("description")
  private String description;

  @SerializedName("allow_userinfos")
  private Users allowUserinfos;

  @SerializedName("allow_partys")
  private Partys allowPartys;

  @SerializedName("allow_tags")
  private Tags allowTags;

  @SerializedName("close")
  private Integer close;

  @SerializedName("redirect_domain")
  private String redirectDomain;

  @SerializedName("report_location_flag")
  private Integer reportLocationFlag;

  @SerializedName("isreportenter")
  private Integer isreportenter;

  @SerializedName("home_url")
  private String homeUrl;

  public static WxCpAgent fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpAgent.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

  @Data
  public static class Users implements Serializable {
    @SerializedName("user")
    private List<User> user;
  }


  @Data
  public class User implements Serializable {
    @SerializedName("userid")
    private String userid;
  }

  @Data
  public class Partys {
    @SerializedName("partyid")
    private List<Integer> partyids = null;
  }

  @Data
  public class Tags {
    @SerializedName("tagid")
    private List<Integer> tagids = null;
  }

}
