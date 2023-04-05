package com.smile.controller;

import com.smile.dto.OrderInfoDTO;
import com.smile.dto.ProductInfoDTO;
import com.smile.groovy.engine.core.domain.ExecuteParams;

import java.util.Date;

/**
 * @Description
 * @ClassName BaseController
 * @Author smile
 * @date 2023.04.05 15:32
 */
public class BaseController {

    /**
     * 构建订单入参
     */
    public ExecuteParams buildOrderParams() {
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOrderId(1000);
        orderInfoDTO.setOrderAmount("1000");
        orderInfoDTO.setOrderName("测试订单");
        orderInfoDTO.setOrderNumber("BG-123987");
        ExecuteParams executeParams = new ExecuteParams();
        executeParams.put("orderInfo", orderInfoDTO);
        return executeParams;
    }

    /**
     * 构建商品入参
     */
    public ExecuteParams buildProductParams() {
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        productInfoDTO.setCreateDate(new Date());
        productInfoDTO.setName("小米手机");
        productInfoDTO.setPrice(10D);
        ExecuteParams executeParams = new ExecuteParams();
        executeParams.put("productInfo", productInfoDTO);
        return executeParams;
    }
}
