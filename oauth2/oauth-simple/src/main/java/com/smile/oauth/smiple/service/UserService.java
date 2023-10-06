package com.smile.oauth.smiple.service;

import com.smile.oauth.smiple.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @ClassName UserService
 * @Author smile
 * @date 2023.10.04 06:41
 */
@Service
public class UserService implements UserDetailsService {

    private List<User> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        List<User> userList = this.userList.stream().filter(user -> user.getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
    }

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User("admin", passwordEncoder.encode("admin"),
                AuthorityUtils.createAuthorityList("ADMIN", "order:list", "order:info")));
        userList.add(new User("user", passwordEncoder.encode("user"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("USER")));
    }
}
