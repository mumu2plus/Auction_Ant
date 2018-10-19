package org.crazyit.auction.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @Column(name = "bid_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 竞价的价格
    @Column(name = "bid_price")
    private double bidPrice;

    // 竞价的日期
    @Column(name = "bid_date")
    private Date bidDate;

    // 本次竞价所竞拍的物品
    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name = "item_id", nullable = false)
    private Item bidItem;

    // 参与竞价的用户
    @ManyToOne(targetEntity = AuctionUser.class)
    @JoinColumn(name = "user_id", nullable = false)
    private AuctionUser bidUser;

    public Bid() {}

    public Bid(Integer id, double bidPrice, Date bidDate) {
        this.id = id;
        this.bidPrice = bidPrice;
        this.bidDate = bidDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public Item getBidItem() {
        return bidItem;
    }

    public void setBidItem(Item bidItem) {
        this.bidItem = bidItem;
    }

    public AuctionUser getBidUser() {
        return bidUser;
    }

    public void setBidUser(AuctionUser bidUser) {
        this.bidUser = bidUser;
    }

    @Override
    public int hashCode() {
        return bidUser.getUsername().hashCode()
                + bidItem.hashCode() * 13 + (int)bidPrice * 19;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == Bid.class) {
            Bid bid = (Bid)obj;
            if (bid.getBidUser().getUsername().equals(bidUser.getUsername())
                    && bid.getBidItem().equals(this.getBidItem())
                    && bid.getBidPrice() == this.getBidPrice()) {
                return true;
            }
        }
        return false;
    }
}
