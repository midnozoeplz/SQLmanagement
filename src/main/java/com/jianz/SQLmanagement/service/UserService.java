package com.jianz.SQLmanagement.service;

import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.User;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/23 0:52
 */
public interface UserService {
    void insertUser(User user);

    ResultBody login(User user);
}
