package com.smile.auth.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  调用Product微服务
 */
@FeignClient("oauth-product")
public interface ProductService {
    @GetMapping("/p1")
    String getProduct1();

    @GetMapping("/p3")
    String getProduct3();
}
