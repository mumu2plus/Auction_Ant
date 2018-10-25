package org.crazyit.auction.business;

import java.util.Date;

public class ItemBean {
    private Integer id;
    private String name;
    private String desc;
    private String remark;
    private String kind;
    private String owner;
    private String winer;
    private String state;
    private double initPrice;
    private double maxPrice;
    private Date addTime;
    private Date endTime;

    public ItemBean(){}

    public ItemBean(Integer id, String name, String desc, String remark,
                    String kind, String owner, String winer, String state,
                    double initPrice, double maxPrice, Date addTime, Date endTime) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.remark = remark;
        this.kind = kind;
        this.owner = owner;
        this.winer = winer;
        this.state = state;
        this.initPrice = initPrice;
        this.maxPrice = maxPrice;
        this.addTime = addTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWiner() {
        return winer;
    }

    public void setWiner(String winer) {
        this.winer = winer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(double initPrice) {
        this.initPrice = initPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
