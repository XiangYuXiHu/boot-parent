package com.smile.order.mapper;

import com.simle.common.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @ClassName OrderRepository
 * @Author smile
 * @date 2022.04.09 13:36
 */
@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     *
     * @param order
     */
    void create(Order order);

    /**
     * 修改订单状态，从零改为1
     *
     * @param userId
     * @param status
     */
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
