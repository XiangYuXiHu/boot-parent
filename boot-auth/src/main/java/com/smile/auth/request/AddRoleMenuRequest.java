package com.smile.auth.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @ClassName AddRoleMenuRequest
 * @Author smile
 * @date 2022.04.27 21:50
 */
@Getter
@Setter
public class AddRoleMenuRequest implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private List<Long> menuIds;

    @Override
    public String toString() {
        return "AddRoleMenuRequest{" +
                "roleId=" + roleId +
                ", menuIds=" + menuIds +
                '}';
    }
}
