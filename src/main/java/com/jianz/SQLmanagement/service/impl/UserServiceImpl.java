package com.jianz.SQLmanagement.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianz.SQLmanagement.dao.UserDao;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.Role;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/22 23:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {
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

    public boolean insertUser(User user) {

        User daoUser = userDao.findUser(user.getUsername());
        if (ObjectUtils.isEmpty(daoUser)) {
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);

            userDao.insertUser(user);
//            System.out.println(userDao.getUserId(user.getUsername()));
            userDao.setRole(userDao.getUserId(user.getUsername()));
            return true;
        } else {
            throw new RuntimeException("???????????????");
        }
    }

    @Override
    public ResultBody login(User user) {

        // TODO
        // AuthenticationManager authenticate??????????????????
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // ??????????????????????????????????????????
        if(ObjectUtils.isEmpty(authenticate)){
            throw new RuntimeException("????????????");
        }
        // ???????????????????????????userid????????????jwt ???jwt??????ResultBody?????????
        AccountUser loginUser = (AccountUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJWT(userid);
        Map<String, String> map =  new HashMap<>();
        map.put("token",jwt);
        map.put("userid",userid);
        Role role = userDao.getUserAuthorityInfo(Long.parseLong(userid));
        map.put("role",role.getCode());
        //??????????????????????????????redis???userid??????key
        redisUtil.setCacheObject("login:"+userid,loginUser,3600);

        return ResultBody.success(map);
    }

    @Override
    public ResultBody logout() {
        //??????SecurityContextHolder????????????id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AccountUser loginUser = (AccountUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId().longValue();
        //??????redis?????????
        redisUtil.del("login:"+userid);
        return ResultBody.success("????????????");
    }

    @Override
    public User loadUserByUsername(String username) {
        return userDao.loadUserByUsername(username);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        User user = userDao.loadUserByUserId(userId);

        String authority = "";

        if(redisUtil.hasKey("GrantedAuthority:" + user.getId())){
            authority = redisUtil.getCacheObject("GrantedAuthority:" + user.getId().toString());
        } else{
            Role role = userDao.getUserAuthorityInfo(userId);

            if(ObjectUtils.isNotEmpty(role)){
                String roleCodes = role.getCode();
            }
            redisUtil.setCacheObject("GrantedAuthority:" + user.getId(),authority,60 * 60);
        }

        return authority;
    }

    @Override
    public User loadUserByUserId(Long id) {
        return userDao.loadUserByUserId(id);
    }
}
