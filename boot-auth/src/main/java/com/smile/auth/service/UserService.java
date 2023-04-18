package com.smile.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.auth.entity.User;
import com.smile.auth.request.AddUserRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
public interface UserService extends IService<User> {

    /**
     * 添加用户
     *
     * @param addUserRequest
     */
    boolean addUser(AddUserRequest addUserRequest);

    /**
     * 用户是否存在
     *
     * @param addUserRequest
     * @return
     */
    boolean isUserExist(AddUserRequest addUserRequest);
}
