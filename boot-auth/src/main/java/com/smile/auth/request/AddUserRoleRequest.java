package com.smile.auth.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @ClassName AddUserRoleRequest
 * @Author smile
 * @date 2022.04.28 23:25
 */
@Getter
@Setter
public class AddUserRoleRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    @Override
    public String toString() {
        return "AddUserRoleRequest{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
