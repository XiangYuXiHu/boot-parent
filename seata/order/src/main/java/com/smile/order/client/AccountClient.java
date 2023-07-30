package com.smile.order.client;

import com.simle.common.base.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 调用账户
 *
 * @author 12780
 */
@FeignClient(value = "seata-account")
public interface AccountClient {

    /**
     * 扣减账户金额
     *
     * @param userId
     * @param money
     * @return
     */
    @GetMapping(value = "/account/decrease")
    ResultBean decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
