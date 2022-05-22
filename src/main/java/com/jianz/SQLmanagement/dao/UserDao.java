package com.jianz.SQLmanagement.dao;

import com.jianz.SQLmanagement.pojo.Role;
import com.jianz.SQLmanagement.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 16:34
 */
@Mapper
public interface UserDao {

    User loadUserByUsername(String username);

    Role getRoleByUid(Integer uid);

    void insertUser(User user);
}
