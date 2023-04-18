package com.smile.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.auth.dto.MenuVO;
import com.smile.auth.entity.Menu;
import com.smile.auth.request.AddMenuRequest;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
public interface MenuService extends IService<Menu> {

    /**
     * 添加菜单
     *
     * @param request
     * @return
     */
    boolean addMenu(AddMenuRequest request);

    /**
     * 判断菜单是否存在
     *
     * @param request
     * @return
     */
    boolean isMenuExist(AddMenuRequest request);

    /**
     * 菜单树
     *
     * @return
     */
    List<MenuVO> menuTree();

    /**
     * 查询用户的可看菜单
     *
     * @param userId
     * @return
     */
    List<MenuVO> queryMenusByUserId(Long userId);

    /**
     * 是否允许访问
     *
     * @param userId
     * @param menuCode
     * @return
     */
    boolean isAllowOperator(Long userId, String menuCode);
}
