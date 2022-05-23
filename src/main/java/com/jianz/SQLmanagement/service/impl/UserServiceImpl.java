package com.jianz.SQLmanagement.service.impl;

import com.jianz.SQLmanagement.dao.UserDao;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.User;
import com.jianz.SQLmanagement.security.AccountUser;
import com.jianz.SQLmanagement.service.UserService;
import com.jianz.SQLmanagement.utils.JwtUtils;
import com.jianz.SQLmanagement.utils.RedisUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/22 23:43
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtil redisUtil;

    public void insertUser(User user){
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        userDao.insertUser(user);
        System.out.println(user.getId());
        userDao.setRole(user.getId());
    }

    @Override
    public ResultBody login(User user) {

        // AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过，给出对应提示
        if(ObjectUtils.isEmpty(authenticate)){
            throw new RuntimeException("登陆失败");
        }
        // 如果认证过了，使用userid生成一个jwt ，jwt存入ResultBody中返回
        AccountUser loginUser = (AccountUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = jwtUtils.generateToken(userid);
        Map<String, String> map =  new HashMap<>();
        map.put("token",jwt);
        //把完整的用户信息存入redis，userid作为key
        redisUtil.setCacheObject("login:"+userid,loginUser,1200);

        return ResultBody.success(map);
    }
}
