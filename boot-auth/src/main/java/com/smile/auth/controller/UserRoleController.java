package com.smile.auth.controller;


import com.smile.auth.dto.BizResponse;
import com.smile.auth.request.AddUserRoleRequest;
import com.smile.auth.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.smile.auth.constants.CodeEnum.USER_ROLE_EXIST;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Slf4j
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加用户角色
     *
     * @param request
     * @return
     */
    @PostMapping("add")
    public BizResponse<Boolean> addUserRole(@RequestBody @Validated AddUserRoleRequest request) {
        log.info("添加用户角色关系为:{}", request);
        boolean userRoleExist = userRoleService.isUserRoleExist(request);
        if (userRoleExist) {
            return BizResponse.failure(USER_ROLE_EXIST);
        }
        boolean isAdd = userRoleService.addUserRole(request);
        return BizResponse.success(isAdd);
    }
}

