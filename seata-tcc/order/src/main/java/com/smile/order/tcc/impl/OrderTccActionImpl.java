package com.smile.order.tcc.impl;

import com.smile.order.mapper.OrderMapper;
import com.smile.order.request.OrderRequest;
import com.smile.order.tcc.OrderTccAction;
import com.smile.order.tcc.ResultHolder;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description
 * @ClassName OrderTccActionImpl
 * @Author smile
 * @date 2023.06.21 23:42
 */
@Slf4j
@Service
public class OrderTccActionImpl implements OrderTccAction {

    @Resource
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean prepareCreateOrder(BusinessActionContext businessActionContext, String orderNo,
                                      Long userId, Long productId, Integer amount, BigDecimal money) {
        log.info("创建订单，第一阶段持久化订单，orderNo=" + orderNo + "订单状态:创建中");
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderNo(orderNo);
        orderRequest.setUserId(userId);
        orderRequest.setProductId(productId);
        orderRequest.setAmount(amount);
        orderRequest.setMoney(money);
        orderRequest.setStatus(0);
        orderMapper.save(orderRequest);
        ResultHolder.setResult(OrderTccAction.class, businessActionContext.getXid(), "pre");
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        String pre = ResultHolder.getResult(OrderTccAction.class, businessActionContext.getXid());
        if (null == pre) {
            return true;
        }
        /**
         * 上下文对象从第一阶段向第二阶段传递时，先转成了json数据，然后还原成上下文对象
         * 其中的整数比较小的会转成Integer类型，所以如果需要Long类型，需要先转换成字符串在用Long.valueOf()解析。
         */
        String orderNo = businessActionContext.getActionContext("orderNo").toString();
        orderMapper.updateStatusByOrderNo(orderNo, 1);
        log.info("创建订单，第二阶段更新订单状态，orderNo=" + orderNo + "订单状态:已完成");
        /**
         * 提交完成后，删除标记
         */
        ResultHolder.removeResult(OrderTccAction.class, businessActionContext.getXid());
        return true;
    }

    /**
     * cancel 撤销
     * <p>
     * 第一阶段没有完成的情况下，不必执行回滚。因为第一阶段有本地事务，事务失败时已经进行了回滚。
     * 如果这里第一阶段成功，而其他全局事务参与者失败，这里会执行回滚
     * 幂等性控制：如果重复执行回滚则直接返回
     *
     * @param businessActionContext
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        /**
         * 检查标记是否存在，如果标记不存在不重复提交
         */
        String pre = ResultHolder.getResult(OrderTccAction.class, businessActionContext.getXid());
        if (null == pre) {
            return true;
        }
        String orderNo = businessActionContext.getActionContext("orderNo").toString();
        orderMapper.deleteByOrderNo(orderNo);
        log.info("创建订单，第二阶段，回滚，orderNo=" + orderNo + "删除订单");
        ResultHolder.removeResult(OrderTccAction.class, businessActionContext.getXid());
        return true;
    }
}
