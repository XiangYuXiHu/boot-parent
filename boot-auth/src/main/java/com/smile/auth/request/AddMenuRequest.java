package com.smile.auth.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加菜单请求
 *
 * @Description
 * @ClassName AddMenuRequest
 * @Author smile
 * @date 2022.04.27 11:08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddMenuRequest implements Serializable {

    /**
     * 名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单编码不能为空")
    private String menuCode;

    /**
     * 父节点
     */
    @NotNull(message = "菜单父节点不能为空")
    private Long parentId;

    /**
     * 节点类型，1文件夹，2页面，3按钮
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer nodeType;

    /**
     * 图标地址
     */
    private String iconUrl;

    /**
     * 排序号
     */
    @NotNull(message = "菜单排序不能为空")
    private Integer sort;

    /**
     * 页面对应的地址
     */
    private String linkUrl;

    @Override
    public String toString() {
        return "AddMenuRequest{" +
                "name='" + name + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", parentId=" + parentId +
                ", nodeType=" + nodeType +
                ", iconUrl='" + iconUrl + '\'' +
                ", sort=" + sort +
                ", linkUrl='" + linkUrl + '\'' +
                '}';
    }
}
