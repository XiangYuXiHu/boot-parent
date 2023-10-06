package com.smile.oauth.jdbc.model;

import lombok.Data;

/**
 * @Description
 * @ClassName PermissionVo
 * @Author smile
 * @date 2023.10.06 10:56
 */
@Data
public class PermissionVo {

    private String id;
    private String code;
    private String description;
    private String url;
}
