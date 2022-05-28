package com.jianz.SQLmanagement.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jianz.SQLmanagement.dao.SysMenuMapper;
import com.jianz.SQLmanagement.dao.UserDao;
import com.jianz.SQLmanagement.pojo.Role;
import com.jianz.SQLmanagement.pojo.User;
import com.jianz.SQLmanagement.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jianz
 * @version 1.0
 * @Description  SpringSecurity TODO
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 15:44
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getUsername,username);
//        User user = userService.getBaseMapper().selectOne(queryWrapper);
        // 上面这个查询不知道为啥不行，被坑了一下午调试bug
        User user = userService.loadUserByUsername(username);
        //如果没有查询到用户就抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误");
        }

//        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));
        List<String> list = sysMenuMapper.selectPermsByUserId(user.getId().longValue());
        //把数据封装成UserDetails返回
        return new AccountUser(user,list);
    }
}
