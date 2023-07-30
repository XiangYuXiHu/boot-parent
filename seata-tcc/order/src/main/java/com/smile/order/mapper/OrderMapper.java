package com.smile.order.mapper;

import com.smile.order.request.OrderRequest;
import org.apache.ibatis.annotations.Param;

/**
 * smile
 */
public interface OrderMapper {
    /**
     * 新增
     *
     * @return
     */
    void save(@Param("order") OrderRequest order);

    /**
     * 根据id，更新状态
     *
     * @param orderNo
     * @param status
     */
    void updateStatusByOrderNo(@Param("orderNo") String orderNo, @Param("status") Integer status);

    /**
     * 根据id，删除
     *
     * @param orderNo
     */
    void deleteByOrderNo(@Param("orderNo") String orderNo);
}
