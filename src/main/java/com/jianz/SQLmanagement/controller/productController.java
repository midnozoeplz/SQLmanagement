package com.jianz.SQLmanagement.controller;

import com.jianz.SQLmanagement.pojo.Product;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.service.impl.ProductServiceImpl;
import com.jianz.SQLmanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jianz
 * @version 1.0
 * @Description admin管理员 对商品添加，展示，删除等管理
 * @Email jianz8153.gmail.com
 * @Date 2022/5/28 15:37
 */
@RestController
public class productController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/product/addOrUpdate")
    public ResultBody addOrUpdateProduct( Product product){

        productService.addOrUpdateProduct(product);

        return ResultBody.success("添加成功");
    }

    @GetMapping("/product/list")
    public ResultBody getList(int page,int limit){

//        System.out.println(page + " "+ limit);
        List<Product> list = productService.getList(page,limit);
        Integer count = productService.getBaseMapper().selectCount(null);
        ArrayList<Object> objects = new ArrayList<>(list);
        objects.add(count);
        return ResultBody.success(objects);
    }

    @PostMapping("/product/delete")
    public ResultBody delete(int id){
        productService.deleteProduct(id);

        return ResultBody.success("删除成功");
    }
}
