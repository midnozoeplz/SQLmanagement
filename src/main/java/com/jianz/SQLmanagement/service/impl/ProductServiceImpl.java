package com.jianz.SQLmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianz.SQLmanagement.dao.ProductMapper;
import com.jianz.SQLmanagement.pojo.Product;
import com.jianz.SQLmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianz
 * @since 2022-05-28
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;

     /**
      * @author Jianz
      * @Description  admin添加一个商品
      * @Return
      * @Date 2022/5/28 16:02
      */
    public void addProduct(Product product){

        productMapper.insert(product);

    }
     /**
      * @author Jianz
      * @Description 获取所有的商品
      * @Return
      * @Date 2022/5/29 0:26
      */
    public List<Product> getList(){
        return  productMapper.selectList(null);
    }

}
