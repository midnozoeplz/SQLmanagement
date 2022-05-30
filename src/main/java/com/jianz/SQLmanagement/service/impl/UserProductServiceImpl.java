package com.jianz.SQLmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianz.SQLmanagement.dao.UserProductMapper;
import com.jianz.SQLmanagement.pojo.UserProduct;
import com.jianz.SQLmanagement.service.UserProductService;
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
public class UserProductServiceImpl extends ServiceImpl<UserProductMapper, UserProduct> implements UserProductService {

        @Autowired
        private UserProductMapper userProductMapper;

         /**
          * @author Jianz
          * @Description 用户添加或更新购物车的商品
          * @Return
          * @Date 2022/5/29 0:29
          */
    public void addToShopping(UserProduct userProduct){

        QueryWrapper<UserProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserProduct::getUserid,userProduct.getUserid()).eq(UserProduct::getProductId,userProduct.getProductId());
        UserProduct product = userProductMapper.selectOne(queryWrapper);
        System.out.println(product);
        if(ObjectUtils.isNotEmpty(product)){
            // 复杂写法
//            UpdateWrapper<UserProduct> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("userid",userProduct.getUserid()).eq("product_id",userProduct.getProductId());
//
            // 最简单的写法
//                int num = product.getNum() + 1;
//                product.setNum(num);
//            userProductMapper.update(product,updateWrapper);

            userProductMapper.updateById(userProduct);
        }
        else {
            userProductMapper.insert(userProduct);
        }
    }

    /**
     * @author Jianz
     * @Description 用户删除购物车的商品
     * @Return
     * @Date 2022/5/30 15:08
     */
     public void deleteProduct(int productId,int userid){
         QueryWrapper<UserProduct> queryWrapper = new QueryWrapper<>();
         queryWrapper.lambda().eq(UserProduct::getUserid,userid).eq(UserProduct::getProductId,productId);
         UserProduct product = userProductMapper.selectOne(queryWrapper);
         if(ObjectUtils.isNotEmpty(product)){
             LambdaQueryWrapper<UserProduct> wrapper = new LambdaQueryWrapper<>();
             wrapper.eq(UserProduct::getId,product.getId());
             userProductMapper.delete(wrapper);
         }
     }

      /**
       * @author Jianz
       * @Description 获取某用户的所有商品
       * @Return
       * @Date 2022/5/30 15:14
       */
      public List<UserProduct> getList(int userid){
          LambdaQueryWrapper<UserProduct> wrapper = new LambdaQueryWrapper<>();
          wrapper.eq(UserProduct::getUserid,userid);
          return userProductMapper.selectList(wrapper);
      }
}
