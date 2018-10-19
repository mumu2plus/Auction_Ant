package org.crazyit.auction.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_remark")
    private String itemRemark;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "addtime")
    private Date addtime;

    @Column(name = "endtime")
    private Date endtime;

    @Column(name = "init_price")
    private double initPrice;

    @Column(name = "max_price")
    private double maxPrice;

    // 该物品的所有者
    @ManyToOne(targetEntity = AuctionUser.class)
    @JoinColumn(name = "owner_id", nullable = false)
    private AuctionUser owner;

    // 该物品所属的种类
    @ManyToOne(targetEntity = Kind.class)
    @JoinColumn(name = "kind_id", nullable = false)
    private Kind kind;

    // 该物品的赢取者
    @ManyToOne(targetEntity = AuctionUser.class)
    @JoinColumn(name = "winer_id", nullable = true)
    private AuctionUser winer;

    // 该物品所处的状态
    @ManyToOne(targetEntity = State.class)
    @JoinColumn(name = "state_id", nullable = false)
    private State itemState;

    // 该物品对应的全部竞价记录
    @OneToMany(targetEntity = Bid.class,
        mappedBy = "bidItem")
    private Set<Bid> bids = new HashSet<>();

    public Item() {}

    public Item(Integer id, String itemName, String itemRemark,
                String itemDesc, Date addtime, Date endtime,
                double initPrice, double maxPrice, AuctionUser owner) {
        this.id = id;
        this.itemName = itemName;
        this.itemRemark = itemRemark;
        this.itemDesc = itemDesc;
        this.addtime = addtime;
        this.endtime = endtime;
        this.initPrice = initPrice;
        this.maxPrice = maxPrice;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemRemark() {
        return itemRemark;
    }

    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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

    public AuctionUser getOwner() {
        return owner;
    }

    public void setOwner(AuctionUser owner) {
        this.owner = owner;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public AuctionUser getWiner() {
        return winer;
    }

    public void setWiner(AuctionUser winer) {
        this.winer = winer;
    }

    public State getItemState() {
        return itemState;
    }

    public void setItemState(State itemState) {
        this.itemState = itemState;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }
}
