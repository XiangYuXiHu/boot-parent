package com.smile.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.auth.entity.RoleMenu;
import com.smile.auth.request.AddRoleMenuRequest;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 添加角色与菜单关系
     *
     * @param addRoleMenuRequest
     * @return
     */
    boolean addRoleMenu(AddRoleMenuRequest addRoleMenuRequest);

    /**
     * 角色-菜单存在
     *
     * @param addRoleMenuRequest
     * @return
     */
    List<Long> getValidRoleMenu(AddRoleMenuRequest addRoleMenuRequest);
}
