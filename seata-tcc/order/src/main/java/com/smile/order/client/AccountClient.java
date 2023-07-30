package com.smile.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author smile
 */
@FeignClient(name = "seata-tcc-account")
public interface AccountClient {

    /**
     * 扣余额
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @GetMapping("/account/decreaseMoney")
    String decreaseMoney(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
