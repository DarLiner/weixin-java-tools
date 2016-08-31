package me.chanjar.weixin.mp.bean;

import java.io.Serializable;

/**
 * @author miller
 *  官方文档中，创建和获取的数据结构不一样。所以采用冗余字段的方式，实现相应的接口
 */
public class Industry implements Serializable {
    private static final long serialVersionUID = -1707184885588012142L;
    private String id;
    private String firstClass;
    private String secondClass;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstClass() {
        return this.firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getSecondClass() {
        return this.secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }
}
