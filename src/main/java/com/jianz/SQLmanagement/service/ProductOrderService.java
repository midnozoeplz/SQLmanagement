package com.jianz.SQLmanagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jianz.SQLmanagement.pojo.MyOrder;
import com.jianz.SQLmanagement.pojo.ProductOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianz
 * @since 2022-06-02
 */
public interface ProductOrderService extends IService<ProductOrder> {

    List<MyOrder> getMyOrders(int userid);

}
