package com.smile.oauth.jdbc.service;

import com.smile.oauth.jdbc.model.PermissionVo;
import com.smile.oauth.jdbc.model.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @ClassName UserService
 * @Author smile
 * @date 2023.10.06 10:51
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UserVo getUserByUsername(String username) {
        String sql = "select id,username,password,status from sys_user where username=?";
        List<UserVo> userList = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserVo.class));
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

    public List<String> getPermissionByUserId(Integer userId) {
        String sql = "select  m.permission_NAME AS code,m.permission_url AS url from sys_user u " +
                "left join sys_user_role r on u.id=r.UID " +
                "left join sys_role_permission p on r.RID=p.RID " +
                "left join sys_permission m on p.PID=m.ID " +
                "where u.id=? ";
        List<PermissionVo> permissions = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionVo.class));
        return permissions.stream().map(PermissionVo::getCode).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo user = getUserByUsername(username);
        if (null == user) {
            //如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        List<String> permissions = getPermissionByUserId(user.getId());
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);
        return User.withUsername(user.getUsername()).password(user.getPassword())
                .authorities(permissionArray).build();
    }
}
