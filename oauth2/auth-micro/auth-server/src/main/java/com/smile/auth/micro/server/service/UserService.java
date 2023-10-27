package com.smile.auth.micro.server.service;

import com.smile.auth.micro.common.entity.SysPermission;
import com.smile.auth.micro.common.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName UserService
 * @Author smile
 * @date 2023.10.22 14:59
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据账号查询用户信息
     *
     * @param username
     * @return
     */
    public SysUser getUserByUsername(String username) {
        String sql = "select id,username,password,status from sys_user where username = ?";
        //连接数据库查询用户
        List<SysUser> list = jdbcTemplate.query(sql, new Object[]{username},
                new BeanPropertyRowMapper<>(SysUser.class));
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据用户id查询用户权限
     *
     * @param userId
     * @return
     */
    public List<String> getPermissionsByUserId(Integer userId) {
        String sql = "SELECT  m.permission_name AS code,m.permission_url AS url FROM  sys_user u\n" +
                "LEFT JOIN sys_user_role r ON u.id=r.UID\n" +
                "LEFT JOIN sys_role_permission p ON r.RID=p.RID\n" +
                "LEFT JOIN sys_permission m ON p.PID=m.ID\n" +
                "WHERE u.id = ?\n";

        List<SysPermission> list = jdbcTemplate.query(sql, new Object[]{userId},
                new BeanPropertyRowMapper<>(SysPermission.class));
        List<String> permissions = new ArrayList<>();
        list.forEach(c -> permissions.add(c.getCode()));
        return permissions;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将来连接数据库根据账号查询用户信息
        SysUser sysUser = getUserByUsername(username);
        if (sysUser == null) {
            //如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        List<String> permissions = getPermissionsByUserId(sysUser.getId());
        String[] permissionArray = permissions.toArray(new String[permissions.size()]);
        UserDetails userDetails = User.withUsername(username)
                .password(sysUser.getPassword()).authorities(permissionArray).build();
        return userDetails;
    }
}
