package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.pojo.Product;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.service.impl.ProductServiceImpl;
import com.jianz.SQLmanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jianz
 * @version 1.0
 * @Description 对商品展示管理
 * @Email jianz8153.gmail.com
 * @Date 2022/5/28 15:37
 */
@RestController
public class productController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/product/add")
    public ResultBody addProduct(@RequestBody Product product){

        productService.addProduct(product);

        return ResultBody.success("添加成功");
    }

    @GetMapping("/product/list")
    public ResultBody getList(){

        List<Product> list = productService.getList();

        return ResultBody.success(list);
    }

}