package com.jianz.SQLmanagement;

import com.jianz.SQLmanagement.dao.SysMenuMapper;
import com.jianz.SQLmanagement.pojo.Product;
import com.jianz.SQLmanagement.pojo.UserProduct;
import com.jianz.SQLmanagement.service.impl.ProductServiceImpl;
import com.jianz.SQLmanagement.service.impl.UserProductServiceImpl;
import com.jianz.SQLmanagement.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/23 0:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserProductServiceImpl userProductService;

    @Test
    public  void main() {
            String s = jwtUtils.generateToken("123");
            System.out.println(s);
    }

    @Test
    public void testSelectPermsById(){
        System.out.println(sysMenuMapper.selectPermsByUserId(2L));
    }

    @Test
    public void testProduct(){
//        Product product = new Product();
//        product.setProductName("HUAWEI nova 9");
//        product.setNum(5);
//        product.setUnitPrice(2599.00);
//        product.setPicture("/static/huaweiNova9");
//        productService.addProduct(product);
          List<Product> list = productService.getList();
            for(Product product : list){
                System.out.println(product);
            }
    }

    @Test
    public void addToShopping(){
        UserProduct userProduct = new UserProduct();
        userProduct.setNum(1);
        userProduct.setPrice(1.00);
        userProduct.setProductName("test");
        userProduct.setUserid(2);
        userProduct.setProductId(2);
        userProductService.addToShopping(userProduct);
    }
}
