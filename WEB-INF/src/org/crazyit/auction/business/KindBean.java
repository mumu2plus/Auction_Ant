package org.crazyit.auction.business;

public class KindBean {
    private int id;
    private String kindName;
    private String kindDesc;

    public KindBean(){}

    public KindBean(int id, String kindName, String kindDesc) {
        this.id = id;
        this.kindName = kindName;
        this.kindDesc = kindDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindDesc() {
        return kindDesc;
    }

    public void setKindDesc(String kindDesc) {
        this.kindDesc = kindDesc;
    }
}
