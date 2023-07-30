package com.smile.order.service;

import com.smile.order.request.OrderRequest;

/**
 * @author smile
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderRequest
     */
    void createOrder(OrderRequest orderRequest);
}
