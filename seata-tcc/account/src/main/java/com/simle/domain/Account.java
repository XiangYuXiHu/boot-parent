package com.simle.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @ClassName Account
 * @Author smile
 * @date 2023.07.29 07:14
 */
@Data
public class Account {

    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 剩余可用额度
     */
    private BigDecimal residue;
    /**
     * TCC事务锁定的金额
     */
    private BigDecimal frozen;
}
