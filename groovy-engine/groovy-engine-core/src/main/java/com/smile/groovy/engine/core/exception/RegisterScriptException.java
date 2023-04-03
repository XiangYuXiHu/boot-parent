package com.smile.groovy.engine.core.exception;

/**
 * @Description
 * @ClassName LoadScriptException
 * @Author smile
 * @date 2023.04.02 20:52
 */
public class RegisterScriptException extends RuntimeException {

    public RegisterScriptException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RegisterScriptException(String message) {
        super(message);
    }
}
