package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/22 21:29
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('system:test:list')")
    public ResultBody hello(){
        return ResultBody.success("hello");
    }
}
