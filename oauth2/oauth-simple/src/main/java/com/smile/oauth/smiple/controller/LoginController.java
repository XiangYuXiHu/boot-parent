package com.smile.oauth.smiple.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @ClassName LoginController
 * @Author smile
 * @date 2022.12.03 22:15
 */
@Slf4j
@Controller
public class LoginController {

    @GetMapping(path = "/login-view")
    public String loginPage() {
        return "login-view";
    }

    @RequestMapping(value = "/login-success")
    @ResponseBody
    public String loginSuccess() {
        return getUsername() + "登陆成功";
    }

    private String getUsername() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (null == principal) {
            return "匿名";
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
