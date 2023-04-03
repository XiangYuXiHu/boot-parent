package com.smile.groovy.engine.core.exception;

/**
 * @Description
 * @ClassName LoadScriptException
 * @Author smile
 * @date 2023.04.02 20:52
 */
public class LoadScriptException extends RuntimeException {

    public LoadScriptException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public LoadScriptException(String message) {
        super(message);
    }
}
