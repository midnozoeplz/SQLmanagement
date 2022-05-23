package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.pojo.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResultBody hello(){
        return ResultBody.success("hello");
    }
}
