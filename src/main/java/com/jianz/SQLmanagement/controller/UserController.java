package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.User;
import com.jianz.SQLmanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private UserServiceImpl userService;

     /**
      * @author Jianz
      * @Description  注册接口
      * @Return
      * @Date 2022/5/23 0:44
      */
    @PostMapping("/register")
    public ResultBody register(User user){

       userService.insertUser(user);

        return ResultBody.success(user);
    }

     /**
      * @author Jianz
      * @Description  登录接口
      * @Return
      * @Date 2022/5/23 0:46
      */
     @PostMapping("/login")
    public ResultBody login(@RequestBody User user){

         return ResultBody.success(userService.login(user));
     }
}
