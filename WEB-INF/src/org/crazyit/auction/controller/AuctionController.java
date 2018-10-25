package org.crazyit.auction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.crazyit.auction.business.ItemBean;
import org.crazyit.auction.domain.Bid;
import org.crazyit.auction.domain.Item;
import org.crazyit.auction.domain.Kind;
import org.crazyit.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auction")
public class AuctionController {
    // 该前端处理类所依赖的业务逻辑组件
    @Autowired
    private AuctionService auctionService;

    // 通过赢取者获取物品的方法
    @ResponseBody
    @GetMapping("/getItemByWiner")
    public Object getItemByWiner(HttpSession sess)
        throws Exception {
        Integer winerId = (Integer)sess.getAttribute("userId");
        List<ItemBean> itembeans = auctionService.getItemByWiner(winerId);
        return itembeans;
    }

    // 获取所有流拍物品的方法
    @ResponseBody
    @GetMapping("/getFailItems")
    public Object getFailItems() throws Exception {
        return auctionService.getFailItems();
    }

    // 处理用户登录的方法
    @ResponseBody
    @PostMapping(value="/loginAjax")
    public Object validLogin(String loginUser, String loginPass,
                             String vercode, HttpSession sess)
        throws Exception {
        String rand = (String)sess.getAttribute("rand");
        if (rand != null && !rand.equalsIgnoreCase(vercode)){
            return "-2";
        }
        int result = auctionService.validLogin(loginUser, loginPass);
        if (result > 0) {
            sess.setAttribute("userId", result);
            return "1";
        }
        return "-1";
    }

    // 获取用户竞价的方法
    @ResponseBody
    @GetMapping("/getBidByUser")
    public Object getBidByUser(HttpSession sess) throws Exception {
        Integer userId = (Integer)sess.getAttribute("userId");
        return auctionService.getBidByUser(userId);
    }

    // 根据属主获取物品的方法
    @ResponseBody
    @GetMapping("/getItemsByOwner")
    public Object getItemsByOwner(HttpSession sess) throws Exception {
        Integer userId = (Integer)sess.getAttribute("userId");
        return auctionService.getItemsByOwner(userId);
    }

    // 获取所有物品种类的方法
    @GetMapping("/getAllKind")
    @ResponseBody
    public Object getAllKind() throws Exception {
        return auctionService.getAllKind();
    }

    // 添加物品的方法
    @ResponseBody
    @PostMapping("/addItem")
    public Object addItem(String name, String desc, String remark,
                          double initPrice, int avail, int kind,
                          HttpSession sess)
        throws Exception {
        Integer userId = (Integer)sess.getAttribute("userId");
        Item item = new Item();
        item.setItemName(name);
        item.setItemDesc(desc);
        item.setItemRemark(remark);
        item.setInitPrice(initPrice);
        int rowNum = auctionService.addItem(item, avail, kind, userId);
        return rowNum > 0 ? "success" : "error";
    }

    // 添加种类的方法
    @ResponseBody
    @PostMapping("/addKind")
    public Object addKind(String name, String desc)
        throws Exception {
        Kind kind = new Kind();
        kind.setKindName(name);
        kind.setKindDesc(desc);
        int rowNum = auctionService.addKind(kind);
        return rowNum > 0 ? "success" : "error";
    }

    // 根据种类获取物品的方法
    @ResponseBody
    @GetMapping("/getItemsByKind")
    public Object getItemsByKind(int kindId) throws Exception {
        return auctionService.getItemsByKind(kindId);
    }

    // 添加竞价记录的方法
    @ResponseBody
    @PostMapping("/addBid")
    public Object addBid(int itemId, double bidPrice, HttpSession sess)
        throws Exception {
        Integer userId = (Integer)sess.getAttribute("userId");
        Bid bid = new Bid();
        bid.setBidPrice(bidPrice);
        int id = auctionService.addBid(itemId, bid, userId);
        return id > 0 ? "success" : "error";
    }
}
