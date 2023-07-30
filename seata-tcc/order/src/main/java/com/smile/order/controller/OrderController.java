package com.smile.order.controller;

import com.smile.order.request.OrderRequest;
import com.smile.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName OrderController
 * @Author smile
 * @date 2023.07.29 06:40
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.createOrder(orderRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail：创建订单失败！";
        }
        return "success：创建订单成功！";
    }
}
