package org.crazyit.auction.dao;

import java.util.List;

import org.crazyit.auction.domain.Bid;
import org.crazyit.common.dao.BaseDao;

public interface BidDao extends BaseDao<Bid> {
    List<Bid> findByUser(Integer userId);
}
