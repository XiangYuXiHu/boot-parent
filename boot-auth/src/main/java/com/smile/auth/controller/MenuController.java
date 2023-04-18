package com.smile.auth.controller;


import com.smile.auth.dto.BizResponse;
import com.smile.auth.dto.MenuVO;
import com.smile.auth.request.AddMenuRequest;
import com.smile.auth.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.smile.auth.constants.CodeEnum.MENU_EXIST;


/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 添加菜单
     *
     * @param request
     */
    @PostMapping("add")
    public BizResponse<Boolean> addMenu(@RequestBody @Validated AddMenuRequest request) {
        log.info("添加菜单的请求:{}", request);
        //todo 分布式锁
        boolean menuExist = menuService.isMenuExist(request);
        if (menuExist) {
            return BizResponse.failure(MENU_EXIST);
        }
        boolean result = menuService.addMenu(request);
        return BizResponse.success(result);
    }


    /**
     * 查询菜单树结构
     *
     * @return
     */
    @GetMapping("tree")
    public BizResponse<List<MenuVO>> queryMenuTree() {
        log.info("查询菜单树结构");
        List<MenuVO> menuVOS = menuService.menuTree();
        return BizResponse.success(menuVOS);
    }

    /**
     * 查询用户的可查看的菜单
     *
     * @param userId
     * @return
     */
    @GetMapping("user")
    public BizResponse<List<MenuVO>> queryMenus(@RequestParam("userId") Long userId) {
        log.info("查看用户:{}的菜单列表", userId);
        List<MenuVO> menuVOS = menuService.queryMenusByUserId(userId);
        return BizResponse.success(menuVOS);
    }
}

