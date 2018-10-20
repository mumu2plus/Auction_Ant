package org.crazyit.auction.dao.impl;

import java.util.List;

import org.crazyit.auction.dao.ItemDao;
import org.crazyit.auction.domain.Item;
import org.crazyit.common.dao.impl.BaseDaoHibernate4;

public class ItemDaoHibernate
    extends BaseDaoHibernate4<Item> implements ItemDao{
    @Override
    public List<Item> findByKind(Integer kindId) {
        return find(
                "from Item as i where i.kind.id=?0 and i.itemState.id=1",
                kindId
        );
    }

    @Override
    public List<Item> findByOwner(Integer userId) {
        return find("from Item as i where i.owner.id=?0 and i.itemState.id=1"
        , userId);
    }

    @Override
    public List<Item> findByWiner(Integer userId) {
        return find("from Item as i where i.winer.id=?0 and i.itemState.id=2"
        , userId);
    }

    @Override
    public List<Item> findByState(Integer stateId) {
        return find("from Item as i where i.itemState.id=?0"
        , stateId);
    }
}
