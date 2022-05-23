package com.jianz.SQLmanagement.security;


import com.jianz.SQLmanagement.dao.UserDao;
import com.jianz.SQLmanagement.pojo.Role;
import com.jianz.SQLmanagement.pojo.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jianz
 * @version 1.0
 * @Description  SpringSecurity
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 15:44
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.查询用户
        User user = userDao.loadUserByUsername(username);
        if(ObjectUtils.isEmpty(user))
            throw new UsernameNotFoundException("用户名不正确！");

        // 2.查询权限信息
        Role role = userDao.getRoleByUid(user.getId());
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        AccountUser accountUser = new AccountUser(user,roles);
        return accountUser;
    }
}
