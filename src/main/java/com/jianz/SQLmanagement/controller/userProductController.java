package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.service.UserProductService;
import com.jianz.SQLmanagement.service.impl.UserProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jianz
 * @version 1.0
 * @Description  对单个用户购物车管理
 * @Email jianz8153.gmail.com
 * @Date 2022/5/28 15:39
 */
@RestController
public class userProductController {
        @Autowired
        private UserProductServiceImpl userProductService;

}
