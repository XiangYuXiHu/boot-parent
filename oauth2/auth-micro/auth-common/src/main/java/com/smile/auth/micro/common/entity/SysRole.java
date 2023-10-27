package com.smile.auth.micro.common.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Description
 * @ClassName Role
 * @Author smile
 * @date 2023.10.21 06:05
 */
@Data
public class SysRole implements GrantedAuthority {
    private Integer id;
    private String roleName;
    private String roleDesc;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
