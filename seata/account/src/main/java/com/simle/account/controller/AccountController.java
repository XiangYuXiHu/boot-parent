package com.simle.account.controller;

import com.simle.account.service.AccountService;
import com.simle.common.base.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description
 * @ClassName AccountController
 * @Author smile
 * @date 2022.04.09 13:56
 */
@RequestMapping("/account")
@RestController
public class AccountController {

    @Resource
    AccountService accountService;

    @GetMapping(value = "decrease", produces = "application/json;charset=UTF-8")
    public ResultBean decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) {
        accountService.decrease(userId, money);
        return ResultBean.success("扣减账户余额成功！");
    }

}

