package com.smile.oauth.jdbc.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Description
 * @ClassName RoleVo
 * @Author smile
 * @date 2023.10.06 10:54
 */
@Data
public class RoleVo implements GrantedAuthority {

    private Integer id;
    private String roleName;
    private String roleDesc;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
