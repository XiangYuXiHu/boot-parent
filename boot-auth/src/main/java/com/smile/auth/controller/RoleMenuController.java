package com.smile.auth.controller;


import com.smile.auth.dto.BizResponse;
import com.smile.auth.request.AddRoleMenuRequest;
import com.smile.auth.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.smile.auth.constants.CodeEnum.ROLE_MENU_EXIST;

/**
 * <p>
 * 角色菜单关系表 前端控制器
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Slf4j
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 添加角色
     *
     * @param request
     * @return
     */
    @PostMapping("add")
    public BizResponse<Boolean> addRoleMenu(@RequestBody @Validated AddRoleMenuRequest request) {
        //todo 分布式锁
        log.info("添加角色菜单请求参数:{}", request);
        List<Long> validRoleMenu = roleMenuService.getValidRoleMenu(request);
        if (CollectionUtils.isEmpty(validRoleMenu)) {
            return BizResponse.failure(ROLE_MENU_EXIST);
        }
        request.setMenuIds(validRoleMenu);
        boolean isAdd = roleMenuService.addRoleMenu(request);
        return BizResponse.success(isAdd);
    }

    //todo 查询角色-菜单表
}

