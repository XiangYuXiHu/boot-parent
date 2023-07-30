package com.smile.order.service;

import com.simle.common.domain.Order;

/**
 * 订单服务
 *
 * @author 12780
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param order
     */
    void create(Order order);
}
