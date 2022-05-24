package com.jianz.SQLmanagement.dao;

import com.jianz.SQLmanagement.pojo.Role;
import com.jianz.SQLmanagement.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    // 备注：使用@Param来确保在xml里能通过#{}来获取的到
    void setRole(@Param("uid") Integer uid);

    Integer getUserId(String username);
    // 保证用户名的唯一性
    User findUser(String username);
}
