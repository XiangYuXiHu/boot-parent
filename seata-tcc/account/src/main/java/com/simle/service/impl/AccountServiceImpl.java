package com.simle.service.impl;

import com.simle.service.AccountService;
import com.simle.tcc.AccountTccAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description
 * @ClassName AccountServiceImpl
 * @Author smile
 * @date 2023.07.29 07:20
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountTccAction accountTccAction;

    @Override
    public void decreaseMoney(Long userId, BigDecimal money) {
        accountTccAction.prepareDecreaseMoney(null, userId, money);
    }
}
