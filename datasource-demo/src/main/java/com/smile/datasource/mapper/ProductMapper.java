package com.smile.datasource.mapper;


import com.smile.datasource.entity.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品mapper
 */
public interface ProductMapper {

    /**
     * 从主库查询所有商品
     *
     * @return
     */
    @Select("select * from product")
    List<Product> findAllProductFromMaster();

    /**
     * 从从库查询所有商品
     *
     * @return
     */
    @Select("select * from product")
    List<Product> findAllProductFromSlave();
}