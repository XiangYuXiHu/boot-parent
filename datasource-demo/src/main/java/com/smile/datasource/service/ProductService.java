package com.smile.datasource.service;

import com.smile.datasource.entity.Product;
import com.smile.datasource.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品service
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<Product> findAllProductFromMaster() {
        List<Product> products = productMapper.findAllProductFromMaster();
        System.out.println("products = " + products);
        return products;
    }

    public List<Product> findAllProductFromSlave() {
        List<Product> products = productMapper.findAllProductFromSlave();
        System.out.println("products = " + products);
        return products;
    }

}
