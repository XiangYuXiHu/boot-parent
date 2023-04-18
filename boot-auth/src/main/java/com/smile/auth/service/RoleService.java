package com.smile.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.auth.dto.RoleVO;
import com.smile.auth.entity.Role;
import com.smile.auth.request.AddRoleRequest;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
public interface RoleService extends IService<Role> {

    /**
     * 添加角色
     *
     * @param request
     * @return
     */
    boolean addRole(AddRoleRequest request);

    /**
     * 判断角色是否存在
     *
     * @param request
     * @return
     */
    boolean isRoleExist(AddRoleRequest request);

    /**
     * 查询角色列表
     *
     * @return
     */
    List<RoleVO> listRole();
}
