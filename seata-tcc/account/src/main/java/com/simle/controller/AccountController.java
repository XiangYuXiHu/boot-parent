package com.simle.controller;


import com.simle.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * account
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 账户扣钱
     *
     * @param userId
     * @param money
     * @return
     */
    @GetMapping("decreaseMoney")
    public String decreaseMoney(Long userId, BigDecimal money) {
        accountService.decreaseMoney(userId, money);
        return "Account 扣减金额成功！";
    }
}
