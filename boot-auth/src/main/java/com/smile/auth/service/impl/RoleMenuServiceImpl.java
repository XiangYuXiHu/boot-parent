package com.smile.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.auth.entity.RoleMenu;
import com.smile.auth.mapper.RoleMenuMapper;
import com.smile.auth.request.AddRoleMenuRequest;
import com.smile.auth.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Slf4j
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public boolean addRoleMenu(AddRoleMenuRequest addRoleMenuRequest) {
        List<Long> menuIds = addRoleMenuRequest.getMenuIds();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(addRoleMenuRequest.getRoleId());
            roleMenu.setMenuId(menuId);
            save(roleMenu);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<Long> getValidRoleMenu(AddRoleMenuRequest addRoleMenuRequest) {
        Long roleId = addRoleMenuRequest.getRoleId();
        List<Long> menuIds = addRoleMenuRequest.getMenuIds();
        List<Long> addMenuIds = new ArrayList<>();
        for (Long menuId : menuIds) {
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            queryWrapper.eq("menu_id", menuId);
            RoleMenu roleMenu = getOne(queryWrapper);
            if (null == roleMenu) {
                addMenuIds.add(menuId);
            } else {
                log.info("角色:{}菜单:{}已经存在", roleId, menuId);
            }
        }
        return addMenuIds;
    }


}
