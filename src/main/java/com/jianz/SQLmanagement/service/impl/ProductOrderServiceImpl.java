package com.jianz.SQLmanagement.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianz.SQLmanagement.dao.ProductOrderMapper;
import com.jianz.SQLmanagement.pojo.MyOrder;
import com.jianz.SQLmanagement.pojo.ProductOrder;
import com.jianz.SQLmanagement.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianz
 * @since 2022-06-02
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements ProductOrderService {

    @Autowired
    private ProductOrderMapper productOrderMapper;


    @Override
    public List<MyOrder> getMyOrders(int userid) {
        return productOrderMapper.getMyOrders(userid);
    }
}
