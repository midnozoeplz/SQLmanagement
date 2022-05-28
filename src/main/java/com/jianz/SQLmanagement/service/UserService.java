package com.jianz.SQLmanagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.User;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/23 0:52
 */
public interface UserService extends IService<User> {
    boolean insertUser(User user);

    ResultBody login(User user);

    ResultBody logout();

    User loadUserByUsername(String username);

    String getUserAuthorityInfo(Long userId);

    User loadUserByUserId(Long id);
}
