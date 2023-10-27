package com.smile.auth.prouct.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  商品服务：假设这个服务不公开，order服务会调它，调用时将token给Product
 */
@RestController
public class ProductController {

    @PreAuthorize("hasAuthority('p1')")
    @GetMapping("/p1")
    public String getProduct1(){
       return "商品1";
    }

    @PreAuthorize("hasAuthority('p3')")
    @GetMapping("/p3")
    public String getProduct3(){
        return "商品3";
    }
}
