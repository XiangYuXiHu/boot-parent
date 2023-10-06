package com.smile.oauth.jdbc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 12780
 */
@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login-view")
    public String loginPage() {
        return "login-view";
    }


    @RequestMapping(value = "/login-success")
    @ResponseBody
    public String loginSuccess() {
        return getUsername() + " 登录成功";
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    private String getUsername() {
        String username = null;
        //当前认证通过的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //用户身份
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            username = "匿名";
        }
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
