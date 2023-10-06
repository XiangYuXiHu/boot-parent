package com.smile.oauth.smiple.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName OrderController
 * @Author smile
 * @date 2022.12.03 22:11
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @PreAuthorize("hasAuthority('order:list')")
    @RequestMapping("list")
    public List<String> getOrderList() {
        List<String> list = new ArrayList<>();
        list.add("订单:鞋子");
        list.add("订单:袜子");
        return list;
    }

    @PreAuthorize("hasAuthority('order:info')")
    @RequestMapping("info")
    public String info() {
        return "订单详情";
    }

    @RequestMapping("info2")
    public String info2() {
        return "订单详情2";
    }

    @PreAuthorize("hasAuthority('order:info3')")
    @RequestMapping("info3")
    public String info3() {
        return "订单详情";
    }
}
