package com.smile.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.auth.entity.UserRole;
import com.smile.auth.request.AddUserRoleRequest;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 添加用户角色关系
     *
     * @param addUserRoleRequest
     * @return
     */
    boolean addUserRole(AddUserRoleRequest addUserRoleRequest);

    /**
     * 用户角色存在
     *
     * @param addUserRoleRequest
     * @return
     */
    boolean isUserRoleExist(AddUserRoleRequest addUserRoleRequest);
}
