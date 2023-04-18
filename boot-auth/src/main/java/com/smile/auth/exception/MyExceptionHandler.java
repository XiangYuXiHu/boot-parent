package com.smile.auth.exception;

import com.smile.dto.BizResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName MyExceptionHandler
 * @Author smile
 * @date 2022.04.29 23:13
 */
@Slf4j
@RestController
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    public BizResponse<String> exceptionHandler(Exception e) {
        return BizResponse.failure(e.getMessage());
    }
}