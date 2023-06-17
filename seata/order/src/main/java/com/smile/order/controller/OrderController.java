package com.smile.order.controller;

import com.smile.common.base.ResultBean;
import com.smile.common.domain.Order;
import com.smile.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName OrderController
 * @Author smile
 * @date 2022.04.09 13:34
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;


    @GetMapping("/order/create")
    public ResultBean<String> create(Order order) {
        orderService.create(order);
        return ResultBean.success("订单创建成功");
    }


}
