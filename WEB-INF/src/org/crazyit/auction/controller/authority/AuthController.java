package org.crazyit.auction.controller.authority;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auction")
public class AuthController {
    @RequestMapping(value = "/authLogin")
    @ResponseBody
    public String preHandle(HttpSession session) throws Exception {
        Integer userId = (Integer)session.getAttribute("userId");
        if (userId == null || userId <= 0) {
            return "false";
        } else {
            return "true";
        }
    }
}
