package com.smile.auth.dto;


import com.smile.auth.constants.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.smile.auth.constants.CodeEnum.FAILURE;
import static com.smile.auth.constants.CodeEnum.SUCCESS;


/**
 * 响应消息
 *
 * @Description
 * @ClassName BizResponse
 * @Author smile
 * @date 2022.04.27 11:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BizResponse<T> {

    private int code;
    private String message;
    private T data;

    /**
     * 操作成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BizResponse<T> success(T data) {
        return new BizResponse(SUCCESS.getCode(), SUCCESS.getDesc(), data);
    }

    /**
     * 操作失败
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BizResponse<T> failure(T data) {
        return new BizResponse<>(FAILURE.getCode(), FAILURE.getDesc(), data);
    }

    /**
     * 操作失败
     *
     * @param codeEnum
     * @param <T>
     * @return
     */
    public static <T> BizResponse<T> failure(CodeEnum codeEnum) {
        return new BizResponse<>(codeEnum.getCode(), codeEnum.getDesc(), null);
    }

    @Override
    public String toString() {
        return "BizResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
