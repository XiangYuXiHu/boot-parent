package com.smile.common.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description
 * @ClassName ResultBean
 * @Author smile
 * @date 2022.04.09 11:11
 */
@Getter
@Setter
public class ResultBean<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public ResultBean() {
    }

    public ResultBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultBean success(T data) {
        return new ResultBean(0, "操作成功", data);
    }

    public static <T> ResultBean failure(T data) {
        return new ResultBean(1024, "操作失败", data);
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
