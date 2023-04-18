package com.smile.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单传参
 *
 * @Description
 * @ClassName MenuVO
 * @Author smile
 * @date 2022.04.27 11:05
 */
@Getter
@Setter
public class MenuVO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 节点类型，1文件夹，2页面，3按钮
     */
    private Integer nodeType;

    /**
     * 图标地址
     */
    private String iconUrl;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 页面对应的地址
     */
    private String linkUrl;

    /**
     * 层次
     */
    private Integer level;

    /**
     * 树id的路径 整个层次上的路径id，逗号分隔，想要找父节点特别快
     */
    private String path;

    /**
     * 子菜单集合
     */
    private List<MenuVO> childMenu;

    @Override
    public String toString() {
        return "MenuVO{" +
                "name='" + name + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", parentId=" + parentId +
                ", nodeType=" + nodeType +
                ", iconUrl='" + iconUrl + '\'' +
                ", sort=" + sort +
                ", linkUrl='" + linkUrl + '\'' +
                ", level=" + level +
                ", path='" + path + '\'' +
                '}';
    }
}
