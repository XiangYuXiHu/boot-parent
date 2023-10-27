package com.smile.auth.micro.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @RequestMapping("list")
    public List<String> getOrderList() {
        List<String> list = new ArrayList<>();
        list.add("订单1：xxx");
        list.add("订单2：xxx");
        return list;
    }

    @PreAuthorize("hasAuthority('order:info')")
    @RequestMapping("info")
    public String info() {
        return "小姐姐：蕾丝边内衣：xxx";
    }

    @PreAuthorize("hasAuthority('order:info2')")
    @RequestMapping("info2")
    public String info2() {
        return "小哥哥：王者荣耀：xxx";
    }

    @RequestMapping("info3")
    public String info3() {
        return "大叔：柴米油盐：xxx";
    }
}
