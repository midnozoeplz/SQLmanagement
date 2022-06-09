package com.jianz.SQLmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianz.SQLmanagement.dao.ProductMapper;
import com.jianz.SQLmanagement.pojo.Product;
import com.jianz.SQLmanagement.service.ProductService;
import org.apache.commons.lang3.ObjectUtils;
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
    public void addOrUpdateProduct(Product product){

        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getProductId,product.getProductId());
        if(ObjectUtils.isNotEmpty(productMapper.selectOne(wrapper))){
            productMapper.updateById(product);
        }
        else {
            productMapper.insert(product);
        }
    }
     /**
      * @author Jianz
      * @Description 获取所有的商品
      * @Return
      * @Date 2022/5/29 0:26
      */
    public List<Product> getList(int page,int limit){
        Page<Product> productPage = new Page<>(page,limit);
        Page<Product> page1 = new LambdaQueryChainWrapper<>(productMapper).page(productPage);
        return page1.getRecords();
    }

     /**
      * @author Jianz
      * @Description admin删除一个商品
      * @Return
      * @Date 2022/5/30 14:51
      */
     public void deleteProduct(int id){
         LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(Product::getProductId,id);
         productMapper.delete(wrapper);
     }

}
