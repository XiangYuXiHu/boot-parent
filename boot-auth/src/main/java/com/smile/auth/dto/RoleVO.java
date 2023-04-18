package com.smile.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色
 *
 * @Description
 * @ClassName RoleVO
 * @Author smile
 * @date 2022.04.27 21:17
 */
@Getter
@Setter
public class RoleVO implements Serializable {

    private Long id;

    private String code;

    private String name;

    @Override
    public String toString() {
        return "RoleVO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
