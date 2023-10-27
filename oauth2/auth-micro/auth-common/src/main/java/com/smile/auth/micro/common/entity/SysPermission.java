package com.smile.auth.micro.common.entity;

import lombok.Data;

/**
 * @Description
 * @ClassName Permission
 * @Author smile
 * @date 2023.10.21 06:06
 */
@Data
public class SysPermission {
    private String id;
    private String code;
    private String description;
    private String url;
}
