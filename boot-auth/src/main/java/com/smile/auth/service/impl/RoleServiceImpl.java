package com.smile.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.auth.dto.RoleVO;
import com.smile.auth.entity.Role;
import com.smile.auth.mapper.RoleMapper;
import com.smile.auth.request.AddRoleRequest;
import com.smile.auth.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public boolean addRole(AddRoleRequest request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        return save(role);
    }

    @Override
    public boolean isRoleExist(AddRoleRequest request) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", request.getCode());
        queryWrapper.eq("name", request.getName());
        Role role = getOne(queryWrapper);
        return null != role;
    }

    @Override
    public List<RoleVO> listRole() {
        List<Role> roles = list();
        if (!CollectionUtils.isEmpty(roles)) {
            List<RoleVO> roleVOS = roles.stream().map(role -> {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(role, roleVO);
                return roleVO;
            }).collect(Collectors.toList());
            return roleVOS;
        }
        return null;
    }
}
