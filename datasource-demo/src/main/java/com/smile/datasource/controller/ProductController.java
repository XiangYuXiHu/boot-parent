package com.smile.datasource.controller;

import com.smile.datasource.anno.DynamicDataSource;
import com.smile.datasource.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品controller
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ApplicationContext applicationContext;

    @DynamicDataSource("master")
    @GetMapping("/find-from-master")
    public String findAllProductFromMaster() {
        productService.findAllProductFromMaster();
        return "find from master success";
    }

    @DynamicDataSource("slave")
    @GetMapping("/find-from-slave")
    public String findAllProductFromSlave() {
        productService.findAllProductFromSlave();
        applicationContext.getBean(ProductController.class).findAllProductFromMaster();
        return "find from slave success";
    }

    @GetMapping("/no")
    public String defaultDatasource() {
        productService.findAllProductFromSlave();
        return "no datasource";
    }

}
