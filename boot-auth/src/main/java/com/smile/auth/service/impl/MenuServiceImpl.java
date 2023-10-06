package com.smile.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.auth.dto.MenuVO;
import com.smile.auth.entity.Menu;
import com.smile.auth.entity.RoleMenu;
import com.smile.auth.entity.UserRole;
import com.smile.auth.exception.BizException;
import com.smile.auth.mapper.MenuMapper;
import com.smile.auth.request.AddMenuRequest;
import com.smile.auth.service.MenuService;
import com.smile.auth.service.RoleMenuService;
import com.smile.auth.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public boolean addMenu(AddMenuRequest request) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        Long parentId = menu.getParentId();
        if (parentId.equals(0L)) {
            menu.setLevel(1);
        } else {
            Menu parentMenu = baseMapper.selectById(parentId);
            if (null == parentMenu) {
                throw new BizException("父节点不存在");
            }
            Integer parentLevel = parentMenu.getLevel();
            menu.setLevel(parentLevel + 1);
            if (StringUtils.isNoneBlank(parentMenu.getPath())) {
                menu.setPath(parentMenu.getPath() + "," + parentMenu.getId());
            } else {
                menu.setPath(parentMenu.getId().toString());
            }
        }
        return save(menu);
    }

    @Override
    public boolean isMenuExist(AddMenuRequest request) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        String menuName = request.getName();
        String menuCode = request.getMenuCode();
        Long parentId = request.getParentId();
        queryWrapper.eq("name", menuName);
        queryWrapper.eq("menu_code", menuCode);
        queryWrapper.eq("parent_id", parentId);
        Menu menu = getOne(queryWrapper);
        return null != menu;
    }

    @Override
    public List<MenuVO> menuTree() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("level", "sort");
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        return transferMenTree(menus, 0L);
    }

    @Override
    public List<MenuVO> queryMenusByUserId(Long userId) {
        List<Menu> menuList = getAllMenuList(userId);
        return transferMenTree(menuList, 0L);
    }

    @Override
    public boolean isAllowOperator(Long userId, String menuCode) {
        List<Menu> menuList = getAllMenuList(userId);
        if (!CollectionUtils.isEmpty(menuList)) {
            return menuList.stream().anyMatch(menuVO -> menuVO.getMenuCode().equalsIgnoreCase(menuCode));
        }
        return false;
    }

    /**
     * 用户查询角色-角色查询菜单（含父菜单）
     *
     * @param userId
     * @return
     */
    private List<Menu> getAllMenuList(Long userId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(userRoles)) {
            Set<Long> menuIds = new HashSet<>();
            userRoles.stream().forEach(ur -> {
                QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
                roleMenuWrapper.eq("role_id", ur.getRoleId());
                List<RoleMenu> roleMenus = roleMenuService.list(roleMenuWrapper);
                if (!CollectionUtils.isEmpty(roleMenus)) {
                    Set<Long> menuIdPart = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
                    menuIds.addAll(menuIdPart);
                }
            });
            QueryWrapper<Menu> menuQuery = new QueryWrapper<>();
            menuQuery.in("id", menuIds);
            List<Menu> menus = list(menuQuery);
            //获取所有父节点
            Set<Long> allMenuIds = new HashSet<>();
            if (!CollectionUtils.isEmpty(menus)) {
                menus.stream().forEach(menu -> {
                    allMenuIds.add(menu.getId());
                    String menuPath = menu.getPath();
                    String[] paths = StringUtils.split(menuPath, ",");
                    if (null != paths) {
                        for (String path : paths) {
                            allMenuIds.add(Long.valueOf(path));
                        }
                    }
                });
            }
            QueryWrapper<Menu> allMenuQuery = new QueryWrapper<>();
            allMenuQuery.in("id", allMenuIds);
            List<Menu> menuList = list(allMenuQuery);
            return menuList;
        }
        return null;
    }


    /**
     * 菜单转为树形结构
     *
     * @param menus
     * @param parentId
     * @return
     */
    private List<MenuVO> transferMenTree(List<Menu> menus, Long parentId) {
        List<MenuVO> menuVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                if (parentId.equals(menu.getParentId())) {
                    MenuVO menuVO = new MenuVO();
                    BeanUtils.copyProperties(menu, menuVO);
                    List<MenuVO> childrenMenuVOS = transferMenTree(menus, menu.getId());
                    if (!CollectionUtils.isEmpty(childrenMenuVOS)) {
                        menuVO.setChildMenu(childrenMenuVOS);
                    }
                    menuVOS.add(menuVO);
                }
            }
        }
        return menuVOS;
    }


}
