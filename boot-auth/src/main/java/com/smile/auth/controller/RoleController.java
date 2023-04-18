package com.smile.auth.controller;


import com.smile.auth.anno.CheckPermission;
import com.smile.auth.dto.BizResponse;
import com.smile.auth.dto.RoleVO;
import com.smile.auth.request.AddRoleRequest;
import com.smile.auth.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.smile.auth.constants.CodeEnum.ROLE_EXIST;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @CheckPermission(value = "roleMgr:add")
    @PostMapping("add")
    public BizResponse<Boolean> addRole(@RequestBody @Validated AddRoleRequest request) {
        log.info("添加角色的请求参数:{}", request);
        boolean roleExist = roleService.isRoleExist(request);
        if (roleExist) {
            return BizResponse.failure(ROLE_EXIST);
        }
        boolean isAdded = roleService.addRole(request);
        return BizResponse.success(isAdded);
    }


    @GetMapping("list")
    public BizResponse<List<RoleVO>> listRole() {
        log.info("查询角色列表");
        List<RoleVO> roleVOS = roleService.listRole();
        return BizResponse.success(roleVOS);
    }
}

