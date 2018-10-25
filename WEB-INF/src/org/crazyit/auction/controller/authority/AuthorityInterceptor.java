package org.crazyit.auction.controller.authority;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.crazyit.auction.exception.AuctionException;

public class AuthorityInterceptor {
    // 进行权限检查的方法
    public Object authority(ProceedingJoinPoint jp)
        throws Throwable {
        System.out.println("----------------------=====---------------------");
        HttpSession sess = null;
        // 获取被拦截方法的全部参数
        Object[] args = jp.getArgs();
        // 遍历被拦截方法的全部参数
        for (Object arg : args) {
            if (arg instanceof HttpSession) {
                sess = (HttpSession)arg;
            }
        }
        // 取出HttpSession里的userId属性
        Integer userId = (Integer)sess.getAttribute("userId");
        // 如果HttpSession里的userId属性为null,或小于等于0
        if (userId == null || userId <= 0){
            // 如果还未登录，抛出异常
            Map<String, String> map = new HashMap<>();
            map.put("error", "您还没有登录，必须先登录才能使用该功能");
            return map;
        }
        return jp.proceed();
    }
}
