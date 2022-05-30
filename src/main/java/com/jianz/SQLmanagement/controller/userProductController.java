package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.pojo.UserProduct;
import com.jianz.SQLmanagement.service.UserProductService;
import com.jianz.SQLmanagement.service.impl.UserProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        @GetMapping("/{userid}/product/getList")
        public ResultBody getList(@PathVariable String userid){
                List<UserProduct> list = userProductService.getList(Integer.parseInt(userid));
                return ResultBody.success(list);
        }

        @PostMapping("/{userid}/product/deleteProduct")
        public ResultBody deleteProduct(@PathVariable String userid,int productId){
                userProductService.deleteProduct(productId,Integer.parseInt(userid));
                return ResultBody.success("删除成功");
        }

        @PostMapping("/product/addToShopping")
        public ResultBody addToShopping(UserProduct userProduct){
                userProductService.addToShopping(userProduct);
                return ResultBody.success("添加或修改成功");
        }

}
