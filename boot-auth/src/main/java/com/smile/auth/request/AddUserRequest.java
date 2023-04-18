package com.smile.auth.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 添加用户
 *
 * @Description
 * @ClassName AddUserRequest
 * @Author smile
 * @date 2022.04.28 23:10
 */
@Getter
@Setter
public class AddUserRequest {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    @Override
    public String toString() {
        return "AddUserRequest{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
