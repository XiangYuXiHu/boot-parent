package com.smile.storage.tcc.impl;

import com.smile.storage.domain.Storage;
import com.smile.storage.mapper.StorageMapper;
import com.smile.storage.tcc.ResultHolder;
import com.smile.storage.tcc.StorageTccAction;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName StorageTccActionImpl
 * @Author smile
 * @date 2023.07.30 08:12
 */
@Slf4j
@Service
public class StorageTccActionImpl implements StorageTccAction {

    @Resource
    private StorageMapper storageMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean prepareDecreaseStorage(BusinessActionContext businessActionContext, Long productId, Integer count) {
        log.info("减少商品库存，第一阶段，锁定减少的库存量，productId=" + productId + "， count=" + count);
        Storage storage = storageMapper.findOneByProductId(productId);
        if (storage.getResidue() - count < 0) {
            throw new RuntimeException("库存不足");
        }
        /*
        库存减掉count， 冻结库存增加count
         */
        storageMapper.updateFrozen(productId, storage.getResidue() - count, storage.getFrozen() + count);
        //保存标识
        ResultHolder.setResult(getClass(), businessActionContext.getXid(), "pre");
        int reult = 1 / 0;
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        long productId = Long.parseLong(businessActionContext.getActionContext("productId").toString());
        int count = Integer.parseInt(businessActionContext.getActionContext("count").toString());
        log.info("减少商品库存，第二阶段提交，productId=" + productId + "， count=" + count);
        //防止重复提交
        if (ResultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }
        storageMapper.updateFrozenToUsed(productId, count);
        //删除标识
        ResultHolder.removeResult(getClass(), businessActionContext.getXid());
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        long productId = Long.parseLong(businessActionContext.getActionContext("productId").toString());
        int count = Integer.parseInt(businessActionContext.getActionContext("count").toString());
        log.info("减少商品库存，第二阶段，回滚，productId=" + productId + "， count=" + count);
        //防止重复回滚
        if (ResultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }
        storageMapper.updateFrozenToResidue(productId, count);
        //删除标识
        ResultHolder.removeResult(getClass(), businessActionContext.getXid());
        return true;
    }
}
