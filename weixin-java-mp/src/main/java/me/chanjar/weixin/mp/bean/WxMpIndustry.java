package me.chanjar.weixin.mp.bean;


import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * @author miller
 */
public class WxMpIndustry implements Serializable {
    private static final long serialVersionUID = -7700398224795914722L;
    private Industry primaryIndustry;
    private Industry secondIndustry;

    public static WxMpIndustry fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpIndustry.class);
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public Industry getPrimaryIndustry() {
        return this.primaryIndustry;
    }

    public void setPrimaryIndustry(Industry primaryIndustry) {
        this.primaryIndustry = primaryIndustry;
    }

    public Industry getSecondIndustry() {
        return this.secondIndustry;
    }

    public void setSecondIndustry(Industry secondIndustry) {
        this.secondIndustry = secondIndustry;
    }
}
