package com.smile.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.auth.entity.UserRole;
import com.smile.auth.mapper.UserRoleMapper;
import com.smile.auth.request.AddUserRoleRequest;
import com.smile.auth.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public boolean addUserRole(AddUserRoleRequest addUserRoleRequest) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(addUserRoleRequest, userRole);
        boolean isAdd = save(userRole);
        return isAdd;
    }

    @Override
    public boolean isUserRoleExist(AddUserRoleRequest addUserRoleRequest) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", addUserRoleRequest.getUserId());
        queryWrapper.eq("role_id", addUserRoleRequest.getRoleId());
        UserRole userRole = getOne(queryWrapper);
        return null != userRole;
    }


}
