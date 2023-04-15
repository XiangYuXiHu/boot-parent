package com.smile.datasource.exception;

/**
 * 动态数据源异常
 *
 * @Description
 * @ClassName DynamicDataSourceException
 * @Author smile
 * @date 2023.04.15 15:36
 */
public class DynamicDataSourceException extends RuntimeException {

    public DynamicDataSourceException(String code) {
        super(code);
    }

    public DynamicDataSourceException(String code, Throwable cause) {
        super(code, cause);
    }

    public DynamicDataSourceException(Throwable cause) {
        super(cause);
    }
}
