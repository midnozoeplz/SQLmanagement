package com.jianz.SQLmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jianz.SQLmanagement.pojo.MyOrder;
import com.jianz.SQLmanagement.pojo.ProductOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jianz
 * @since 2022-06-02
 */
@Mapper
public interface ProductOrderMapper extends BaseMapper<ProductOrder> {

    List<MyOrder> getMyOrders(@Param("userid") int userid);

}
