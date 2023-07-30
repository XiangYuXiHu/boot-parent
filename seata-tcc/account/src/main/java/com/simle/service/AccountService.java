package com.simle.service;

import java.math.BigDecimal;

/**
 * @Description
 * @ClassName AccountService
 * @Author smile
 * @date 2023.07.29 07:20
 */
public interface AccountService {

    /**
     * 账户扣钱
     *
     * @param userId 用户id
     * @param money  扣钱金额
     * @return
     */
    void decreaseMoney(Long userId, BigDecimal money);
}
