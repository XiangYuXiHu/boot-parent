package com.smile.mdc.controller;

import com.smile.mdc.domain.Constants;
import com.smile.mdc.wrapper.ThreadPoolExecutorMdcWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName ApiController
 * @Author smile
 * @date 2023.02.11 19:29
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class ApiController {

    @Autowired
    private ThreadPoolExecutorMdcWrapper threadPoolExecutorMdcWrapper;


    @GetMapping("user")
    public String getUser() {
        log.info("thread:{},tradeId:{}", Thread.currentThread().getName(), MDC.get(Constants.TRADE_ID));
        threadPoolExecutorMdcWrapper.submit(() -> {
            log.info("thread:{},tradeId:{}", Thread.currentThread().getName(), MDC.get(Constants.TRADE_ID));
        });
        return "ok";
    }
}
