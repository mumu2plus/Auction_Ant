package org.crazyit.auction.dao;

import org.crazyit.auction.domain.AuctionUser;
import org.crazyit.common.dao.BaseDao;

public interface AuctionUserDao extends BaseDao<AuctionUser> {
    /**
     * 根据用户名，密码查找用户
     * @param username 用户名
     * @param pass 密码
     * @return 指定用户名、密码对应的用户
     */
    AuctionUser findByNameAndPass(String username, String pass);

    /**
     * 根据物品ID、出价查询用户
     * @param itemId 物品id;
     * @param price 出价的价格
     * @return 指定物品、指定竞价对应的用户
     */
    AuctionUser findByItemAndPrice(Integer itemId, Double price);
}
