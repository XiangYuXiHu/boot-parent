package com.smile.account.service;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author 12780
 */
public interface AccountService {

    /**
     * 扣减账户余额
     *
     * @param userId
     * @param money
     */
    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
