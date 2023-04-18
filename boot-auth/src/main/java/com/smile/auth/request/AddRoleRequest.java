package com.smile.auth.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description
 * @ClassName AddRoleRequest
 * @Author smile
 * @date 2022.04.27 18:37
 */
@Getter
@Setter
public class AddRoleRequest implements Serializable {

    /**
     * 编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Override
    public String toString() {
        return "AddRoleRequest{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
