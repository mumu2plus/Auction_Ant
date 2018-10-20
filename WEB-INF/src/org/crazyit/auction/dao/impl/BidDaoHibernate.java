package org.crazyit.auction.dao.impl;

import java.util.List;

import org.crazyit.auction.dao.BidDao;
import org.crazyit.auction.domain.Bid;
import org.crazyit.common.dao.impl.BaseDaoHibernate4;

public class BidDaoHibernate
    extends BaseDaoHibernate4<Bid> implements BidDao{
    @Override
    public List<Bid> findByUser(Integer userId) {
        return find("select bid from Bid bid where bid.bidUser.id=?0"
            , userId);
    }
}
