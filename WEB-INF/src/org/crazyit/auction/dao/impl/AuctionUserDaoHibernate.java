package org.crazyit.auction.dao.impl;

import java.util.List;

import org.crazyit.auction.dao.AuctionUserDao;
import org.crazyit.auction.domain.AuctionUser;
import org.crazyit.common.dao.impl.BaseDaoHibernate4;

public class AuctionUserDaoHibernate
    extends BaseDaoHibernate4<AuctionUser> implements AuctionUserDao{
    /**
     *
     * @param username 用户名
     * @param pass 密码
     * @return 指定用户名、密码对应的用户
     */
    @Override
    public AuctionUser findByNameAndPass(String username, String pass) {
        // 执行HQL查询
        List<AuctionUser> ul = (List<AuctionUser>)find(
                "from AuctionUser au where au.username=?0 and au.userpass=?1",
                username, pass);
        if (ul != null && ul.size() == 1) {
            return (AuctionUser)ul.get(0);
        }
        return null;
    }

    /**
     *
     * @param itemId 物品id;
     * @param price 出价的价格
     * @return 指定物品、指定竞价对应的用户
     */
    @Override
    public AuctionUser findByItemAndPrice(Integer itemId, Double price) {
        // 执行HQL查询
        List<AuctionUser> userList = (List<AuctionUser>)find(
                "select user from AuctionUser user inner join user.bids bid"
                + " where bid.bidItem.id=?0 and bid.bidPrice=?1",
                itemId, price
        );
        // 返回查询得到的第一个Bid对象关联的AuctionUser对象
        if (userList != null && userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }
}
