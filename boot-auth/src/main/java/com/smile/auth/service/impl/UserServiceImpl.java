package com.smile.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.auth.entity.User;
import com.smile.auth.mapper.UserMapper;
import com.smile.auth.request.AddUserRequest;
import com.smile.auth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author smile
 * @since 2022-04-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public boolean addUser(AddUserRequest addUserRequest) {
        User user = new User();
        BeanUtils.copyProperties(addUserRequest, user);
        return save(user);
    }

    @Override
    public boolean isUserExist(AddUserRequest addUserRequest) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", addUserRequest.getName());
        User user = getOne(queryWrapper);
        return null != user;
    }


}
