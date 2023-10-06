package com.smile.oauth.jdbc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 12780
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @RequestMapping("list")
    public List<String> getOrderList() {
        List<String> list = new ArrayList<>();
        list.add("鞋子");
        list.add("袜子");
        return list;
    }

    @PreAuthorize("hasAuthority('order:info')")
    @RequestMapping("info")
    public String info() {
        return "订单详情";
    }

    @PreAuthorize("hasAuthority('order:info2')")
    @RequestMapping("info2")
    public String info2() {
        return "订单明细";
    }

    @RequestMapping("info3")
    public String info3() {
        return "订单物流";
    }
}
