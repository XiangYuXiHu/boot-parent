package com.smile.order.client;

import com.smile.common.base.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用库存
 *
 * @author 12780
 */
@FeignClient(value = "seata-storage")
public interface StorageClient {

    /**
     * 扣减库存
     *
     * @param productId
     * @param count
     * @return
     */
    @GetMapping(value = "/storage/decrease")
    ResultBean decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
