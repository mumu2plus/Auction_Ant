package org.crazyit.auction.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "auction_user")
public class AuctionUser {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 用户名
    @Column(name = "username")
    private String username;

    // 密码
    @Column(name = "userpass")
    private String userpass;

    // 电子邮件
    @Column(name = "email")
    private String email;

    // 根据属主关联的物品实体
    @OneToMany(targetEntity = Item.class,
        mappedBy = "owner")
    private Set<Item> itemsByOwner = new HashSet<>();

    // 根据赢取者关联的物品实体
    @OneToMany(targetEntity = Item.class,
        mappedBy = "winer")
    private Set<Item> itemsByWiner = new HashSet<>();

    // 该用户所参与的全部竞价
    @OneToMany(targetEntity = Bid.class,
        mappedBy = "bidUser")
    private Set<Bid> bids = new HashSet<>();

    public AuctionUser() {}

    public AuctionUser(Integer id, String username, String userpass, String email) {
        this.id = id;
        this.username = username;
        this.userpass = userpass;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Item> getItemsByOwner() {
        return itemsByOwner;
    }

    public void setItemsByOwner(Set<Item> itemsByOwner) {
        this.itemsByOwner = itemsByOwner;
    }

    public Set<Item> getItemsByWiner() {
        return itemsByWiner;
    }

    public void setItemsByWiner(Set<Item> itemsByWiner) {
        this.itemsByWiner = itemsByWiner;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }
}
