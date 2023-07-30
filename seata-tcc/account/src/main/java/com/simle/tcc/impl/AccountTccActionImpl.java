package com.simle.tcc.impl;

import com.simle.domain.Account;
import com.simle.mapper.AccountMapper;
import com.simle.tcc.AccountTccAction;
import com.simle.tcc.ResultHolder;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description
 * @ClassName AccountTccActionImpl
 * @Author smile
 * @date 2023.07.29 07:12
 */
@Slf4j
@Service
public class AccountTccActionImpl implements AccountTccAction {

    @Resource
    private AccountMapper accountMapper;

    /**
     * try
     *
     * @param businessActionContext
     * @param userId
     * @param money
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean prepareDecreaseMoney(BusinessActionContext businessActionContext, Long userId, BigDecimal money) {
        log.info("减少账户金额，第一阶段锁定金额，userId=" + userId + "， money=" + money);
        Account account = accountMapper.findOneByUserId(userId);
        if (account.getResidue().compareTo(money) < 0) {
            throw new RuntimeException("账户金额不足");
        }
        accountMapper.updateFrozen(userId, account.getResidue().subtract(money), money);
        ResultHolder.setResult(getClass(), businessActionContext.getXid(), "pre");
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        Long userId = Long.valueOf(businessActionContext.getActionContext("userId").toString());
        BigDecimal money = new BigDecimal(businessActionContext.getActionContext("money").toString());
        log.info("减少账户金额，第二阶段，提交，userId=" + userId + "， money=" + money);
        if (null == ResultHolder.getResult(getClass(), businessActionContext.getXid())) {
            return true;
        }
        accountMapper.updateFrozenToUsed(userId, money);
        ResultHolder.remove(getClass(), businessActionContext.getXid());
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        long userId = Long.parseLong(businessActionContext.getActionContext("userId").toString());
        BigDecimal money = new BigDecimal(businessActionContext.getActionContext("money").toString());

        //防止重复回滚
        if (ResultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }
        log.info("减少账户金额，第二阶段，回滚，userId=" + userId + "， money=" + money);
        accountMapper.updateFrozenToResidue(userId, money);
        //删除标识
        ResultHolder.remove(getClass(), businessActionContext.getXid());
        return true;
    }
}
