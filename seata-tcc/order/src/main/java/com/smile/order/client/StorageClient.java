package com.smile.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author smile
 */
@FeignClient(name = "seata-tcc-storage")
public interface StorageClient {

    /**
     * 扣库存
     *
     * @param productId 产品id
     * @param count     产品数量
     * @return
     */
    @GetMapping("/storage/decreaseStorage")
    String decreaseStorage(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
