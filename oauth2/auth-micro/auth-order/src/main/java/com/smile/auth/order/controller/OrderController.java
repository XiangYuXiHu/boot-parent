package com.smile.auth.order.controller;

import com.smile.auth.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 12780
 */
@RestController
public class OrderController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/info")
    @PreAuthorize("hasAuthority('info')")//拥有p1权限方可访问此url
    public Map info() throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        //获取用户身份信息
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("userName", username);
        return map;
    }

    @GetMapping(value = "/o1")
    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问此url
    public Map r1() throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        //获取用户身份信息
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("userName", username);
        map.put("order", "订单1：" + productService.getProduct1());
        InetAddress address = InetAddress.getLocalHost();
        map.put("IP", address.getHostAddress());
        return map;
    }

    @GetMapping(value = "/o2")
    @PreAuthorize("hasAuthority('p2')")
    public Map r2() throws UnknownHostException {
        //获取用户身份信息
        Map<String, Object> map = new HashMap<>();
        //获取用户身份信息
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("userName", username);
        map.put("order", "订单2：" + productService.getProduct3());
        InetAddress address = InetAddress.getLocalHost();
        map.put("IP", address.getHostAddress());
        return map;
    }
}