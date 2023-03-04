package com.smile.rate.limit.exception;

/**
 * @Description
 * @ClassName RateLimitException
 * @Author smile
 * @date 2023.03.04 15:20
 */
public class RateLimitException extends RuntimeException {

    public RateLimitException(String message) {
        super(message);
    }
}
