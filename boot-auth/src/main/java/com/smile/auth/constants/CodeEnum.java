package com.smile.auth.constants;

import lombok.Getter;

/**
 * @Description
 * @ClassName Constants
 * @Author smile
 * @date 2022.04.27 11:27
 */
@Getter
public enum CodeEnum {
    SUCCESS(1000, "操作成功"),
    MENU_EXIST(1001, "菜单已经存在"),
    ROLE_EXIST(1002, "角色已经存在"),
    ROLE_MENU_EXIST(1003, "角色菜单已经存在"),
    USER_EXIST(1004, "用户已经存在"),
    USER_ROLE_EXIST(1005, "用户角色已经存在"),
    FAILURE(1024, "操作失败");
    private int code;
    private String desc;

    CodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
