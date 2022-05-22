package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.dao.UserDao;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 17:18
 */
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/register")
    public ResultBody register(User user){

        userDao.insertUser(user);

        return ResultBody.success(user);
    }
}
