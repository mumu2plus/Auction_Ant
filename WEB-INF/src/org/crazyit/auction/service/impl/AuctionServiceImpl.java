package org.crazyit.auction.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.crazyit.auction.business.BidBean;
import org.crazyit.auction.business.ItemBean;
import org.crazyit.auction.business.KindBean;
import org.crazyit.auction.dao.AuctionUserDao;
import org.crazyit.auction.dao.BidDao;
import org.crazyit.auction.dao.ItemDao;
import org.crazyit.auction.dao.KindDao;
import org.crazyit.auction.dao.StateDao;
import org.crazyit.auction.domain.AuctionUser;
import org.crazyit.auction.domain.Bid;
import org.crazyit.auction.domain.Item;
import org.crazyit.auction.domain.Kind;
import org.crazyit.auction.domain.State;
import org.crazyit.auction.exception.AuctionException;
import org.crazyit.auction.service.AuctionService;
import org.hibernate.loader.plan.build.spi.ExpandingCollectionQuerySpace;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class AuctionServiceImpl implements AuctionService{
    static Logger log = Logger.getLogger(
            AuctionServiceImpl.class.getName()
    );

    // 以下是该业务逻辑组件所依赖的dao组件
    private AuctionUserDao userDao;
    private BidDao bidDao;
    private ItemDao itemDao;
    private KindDao kindDao;
    private StateDao stateDao;

    // 业务逻辑组件发送邮件所依赖的两个Bean
    private MailSender mailSender;
    private SimpleMailMessage message;

    // 依赖注入dao方法
    public void setUserDao(AuctionUserDao userDao) {
        this.userDao = userDao;
    }

    public void setBidDao(BidDao bidDao) {
        this.bidDao = bidDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public void setKindDao(KindDao kindDao) {
        this.kindDao = kindDao;
    }

    public void setStateDao(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    // 依赖注入邮件发送bean
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    /**
     * 根据赢取者查询物品
     * @param winerId 赢取者的ID
     * @return 赢取者获得的全部物品
     */
    @Override
    public List<ItemBean> getItemByWiner(Integer winerId) throws AuctionException {
        try {
            List<Item> items = itemDao.findByWiner(winerId);
            List<ItemBean> result = new ArrayList<>();
            for (Iterator<Item> it = items.iterator(); it.hasNext();) {
                ItemBean ib = new ItemBean();
                initItem(ib, it.next());
                result.add(ib);
            }
            return result;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new AuctionException("查询用户所赢取的物品出现异常，请重试");
        }
    }

    /**
     * 查询流拍的全部物品
     * @return 全部流拍物品
     */
    @Override
    public List<ItemBean> getFailItems() throws AuctionException {
        try {
            List<Item> items = itemDao.findByState(3);
            List<ItemBean> result = new ArrayList<>();
            for (Iterator<Item> it = items.iterator(); it.hasNext();){
                ItemBean ib = new ItemBean();
                initItem(ib, it.next());
                result.add(ib);
            }
            return result;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new AuctionException("查询流拍物品出现异常，请重试");
        }
    }

    /**
     * 根据用户名，密码验证登录是否成功
     * @param username 登录的用户名
     * @param pass 登录的密码
     * @return 登录成功返回用户ID，否则返回-1
     */
    @Override
    public int validLogin(String username, String pass) throws AuctionException {
        try{
            AuctionUser u = userDao.findByNameAndPass(username, pass);
            if (u != null) {
                return u.getId();
            }
            return -1;
        } catch (Exception e){
            log.debug(e.getMessage());
            throw new AuctionException("处理用户登录出现异常,请重试");
        }
    }

    /**
     * 查询用户的全部出价
     * @param userId 竞价用户的ID
     * @return 用户的全部出价
     */
    @Override
    public List<BidBean> getBidByUser(Integer userId) throws AuctionException {
        try {
            List<Bid> listBids = bidDao.findByUser(userId);
            List<BidBean> result = new ArrayList<>();
            for (Iterator<Bid> bid = listBids.iterator(); bid.hasNext();){
                BidBean bb = new BidBean();
                initBid(bb, bid.next());
                result.add(bb);
            }
            return result;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new AuctionException("浏览用户的全部竞价出现异常,请重试");
        }
    }

    /**
     * 根据用户查找目前仍在拍卖中的全部物品
     * @param userId 所属者的ID
     * @return 属于当前用户的、处于拍卖中的全部物品。
     */
    @Override
    public List<ItemBean> getItemsByOwner(Integer userId) throws AuctionException {
        try {
            List<ItemBean> result = new ArrayList<>();
            List<Item> items = itemDao.findByOwner(userId);
            for (Iterator<Item> it= items.iterator(); it.hasNext();){
                ItemBean ib = new ItemBean();
                initItem(ib, it.next());
                result.add(ib);
            }
            return result;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new AuctionException("查找目前仍在拍卖中的全部物品,请重新");
        }
    }

    /**
     * 查询全部种类
     * @return 系统中全部全部种类
     */
    @Override
    public List<KindBean> getAllKind() throws AuctionException {
        List<KindBean> result = new ArrayList<>();
        try {
            List<Kind> kl = kindDao.findAll(Kind.class);
            for (Kind k : kl) {
                result.add(new KindBean(k.getId(),
                        k.getKindName(), k.getKindDesc()));
            }
            return result;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new AuctionException("查询全部种类出现异常,请重试");
        }
    }

    /**
     * 添加物品
     * @param item 新增的物品
     * @param avail 有效天数
     * @param kindId 物品种类ID
     * @param userId 添加者的ID
     * @return 新增物品的主键
     */
    @Override
    public int addItem(Item item, int avail, int kindId, Integer userId)
            throws AuctionException {
        try {
            Kind k = kindDao.get(Kind.class, kindId);
            AuctionUser owner = userDao.get(AuctionUser.class, userId);
            // 设置Item的属性
            item.setAddtime(new Date());
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, avail);
            item.setEndtime(c.getTime());
            item.setMaxPrice(item.getInitPrice());
            item.setItemState(stateDao.get(State.class, 1));
            item.setKind(k);
            item.setOwner(owner);
            // 持久化Item对象
            itemDao.save(item);
            return item.getId();
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new AuctionException("添加物品出现异常,请重试");
        }
    }

    /**
     * 添加种类
     * @param kind 新增的种类
     * @return 新增种类的主键
     */
    @Override
    public int addKind(Kind kind) throws AuctionException {
        try {
            kindDao.save(kind);
            return kind.getId();
        } catch (Exception e){
            log.debug(e.getMessage());
            throw new AuctionException("添加种类出现异常,请重试");
        }
    }

    /**
     * 根据产品分类，获取处于拍卖中的全部物品
     * @param kindId 种类id;
     * @return 该类的全部产品
     */
    @Override
    public List<ItemBean> getItemsByKind(int kindId) throws AuctionException {
        List<ItemBean> result = new ArrayList<>();
        try {
            List<Item> items = itemDao.findByKind(kindId);
            for (Iterator<Item> it = items.iterator(); it.hasNext();){
                ItemBean ib = new ItemBean();
                initItem(ib, it.next());
                result.add(ib);
            }
            return result;
        } catch (Exception e){
            log.debug(e.getMessage());
            throw new AuctionException("根据种类获取物品出现异常,请重试");
        }
    }

    /**
     * 根据种类id获取种类名
     * @param kindId 种类id;
     * @return 该种类的名称
     */
    @Override
    public String getKind(int kindId) throws AuctionException {
        try
        {
            Kind  k = kindDao.get(Kind.class , kindId);
            if (k != null)
            {
                return k.getKindName();
            }
            return null;
        }
        catch (Exception ex)
        {
            log.debug(ex.getMessage());
            throw new AuctionException("根据种类id获取种类名称出现异常,请重试");
        }
    }

    /**
     * 根据物品id，获取物品
     * @param itemId 物品id;
     * @return 指定id对应的物品
     */
    @Override
    public ItemBean getItem(int itemId) throws AuctionException {
        try
        {
            Item item = itemDao.get(Item.class , itemId);
            ItemBean ib = new ItemBean();
            initItem(ib , item);
            return ib;
        }
        catch (Exception ex)
        {
            log.debug(ex.getMessage());
            throw new AuctionException("根据物品id获取物品详细信息出现异常,请重试");
        }
    }

    /**
     * 增加新的竞价，并对竞价用户发邮件通知
     * @param itemId 物品id;
     * @param bid 竞价
     * @param userId 竞价用户的ID
     * @return 返回新增竞价记录的ID
     */
    @Override
    public int addBid(int itemId, Bid bid, Integer userId)
            throws AuctionException {
        try {
            AuctionUser au = userDao.get(AuctionUser.class, userId);
            Item item = itemDao.get(Item.class, itemId);
            if (bid.getBidPrice() <= item.getMaxPrice()) {
                return -1;
            }
            item.setMaxPrice(bid.getBidPrice());
            itemDao.save(item);
            // 设置Bid对象的属性
            bid.setBidItem(item);
            bid.setBidUser(au);
            bid.setBidDate(new Date());
            bidDao.save(bid);
            // 准备发送邮件
            SimpleMailMessage msg = new SimpleMailMessage(this.message);
            msg.setTo(au.getEmail());
            msg.setText("Dear "
                + au.getUsername()
                + ", 谢谢你参与竞价，你的竞价物品是："
                + item.getItemName());
            mailSender.send(msg);
            return bid.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug(ex.getMessage());
            throw new AuctionException("处理用户竞价出现异常,请重试");
        }
    }

    /**
     * 根据时间来修改物品的状态、赢取者
     */
    @Override
    public void updateWiner() throws AuctionException {
        System.out.println("------------------" + new Date());
        try {
            List<Item> itemList = itemDao.findByState(1);
            for (int i = 0; i < itemList.size(); i++){
                Item item = itemList.get(i);
                if (!item.getEndtime().after(new Date())){
                    AuctionUser au = userDao.findByItemAndPrice(
                            item.getId(), item.getMaxPrice()
                    );
                    if (au != null) {
                        item.setWiner(au);
                        item.setItemState(stateDao.get(State.class,2));
                        itemDao.save(item);
                    } else {
                        // 设置该物品的状态为"流拍"
                        item.setItemState(stateDao.get(State.class, 3));
                        itemDao.save(item);
                    }
                }
            }
        } catch (Exception ex){
            log.debug(ex.getMessage());
            throw new AuctionException("根据时间来修改物品的状态、赢取者出现异常,请重试");
        }
    }


    /**
     * 将一个Bid对象转换成BidBean对象
     * @param bb BidBean对象
     * @param bid Bid对象
     */
    private void initBid(BidBean bb, Bid bid) {
        bb.setId(bid.getId().intValue());
        if (bid.getBidUser() != null) {
            bb.setUser(bid.getBidUser().getUsername());
        }
        if (bid.getBidItem() != null) {
            bb.setItem(bid.getBidItem().getItemName());
        }
        bb.setPrice(bid.getBidPrice());
        bb.setBidDate(bid.getBidDate());
    }

    /**
     * 将一个Item PO转化成ItemBean的VO
     * @param ib
     * @param item
     */
    private void initItem(ItemBean ib, Item item) {
        ib.setId(item.getId());
        ib.setName(item.getItemName());
        ib.setDesc(item.getItemDesc());
        ib.setRemark(item.getItemRemark());
        if (item.getKind() != null) {
            ib.setKind(item.getKind().getKindName());
        }
        if (item.getOwner() != null) {
            ib.setOwner(item.getOwner().getUsername());
        }
        if (item.getWiner() != null) {
            ib.setWiner(item.getWiner().getUsername());
        }
        ib.setAddTime(item.getAddtime());
        ib.setEndTime(item.getEndtime());
        if (item.getItemState() != null) {
            ib.setState(item.getItemState().getStateName());
        }
        ib.setInitPrice(item.getInitPrice());
        ib.setMaxPrice(item.getMaxPrice());
    }
}
