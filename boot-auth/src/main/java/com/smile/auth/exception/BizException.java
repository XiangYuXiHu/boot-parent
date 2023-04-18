package com.smile.auth.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @ClassName BizException
 * @Author smile
 * @date 2022.04.27 14:01
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    public BizException() {
    }

    public BizException(String desc) {
        super(desc);
    }
}
