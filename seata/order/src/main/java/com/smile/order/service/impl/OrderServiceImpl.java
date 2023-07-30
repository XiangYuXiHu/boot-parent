package com.smile.order.service.impl;

import com.smile.order.client.AccountClient;
import com.smile.order.client.StorageClient;
import com.smile.order.service.OrderService;
import com.simle.common.domain.Order;
import com.smile.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName OrderServiceImpl
 * @Author smile
 * @date 2022.04.09 13:39
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AccountClient accountClient;

    @Resource
    private StorageClient storageClient;

    @Override
    @GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("----->开始新建订单");
        orderMapper.create(order);

        //扣减库存
        log.info("----->订单微服务开始调用库存，做扣减Count");
        storageClient.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存，做扣减end");

        //扣减账户
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountClient.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");

        //修改订单状态，从零到1代表已经完成
        log.info("----->修改订单状态开始");
        orderMapper.update(order.getUserId(), 0);
        log.info("----->修改订单状态结束");

        log.info("----->下订单结束了");
    }
}
