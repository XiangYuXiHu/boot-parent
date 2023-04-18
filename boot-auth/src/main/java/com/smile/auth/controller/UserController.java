package com.smile.auth.controller;


import com.smile.auth.dto.BizResponse;
import com.smile.auth.request.AddUserRequest;
import com.smile.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.smile.auth.constants.CodeEnum.USER_EXIST;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 添加用户
     *
     * @param request
     * @return
     */
    @PostMapping("add")
    public BizResponse<Boolean> addUser(@RequestBody @Validated AddUserRequest request) {
        log.info("添加用户:{}", request);
        boolean userExist = userService.isUserExist(request);
        if (userExist) {
            return BizResponse.failure(USER_EXIST);
        }
        boolean isAdd = userService.addUser(request);
        return BizResponse.success(isAdd);
    }

    //todo 用户列表
}

