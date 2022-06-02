package com.jianz.SQLmanagement.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jianz.SQLmanagement.pojo.MyOrder;
import com.jianz.SQLmanagement.pojo.OrderIdProductId;
import com.jianz.SQLmanagement.pojo.ProductOrder;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.service.OrderIdProductIdService;
import com.jianz.SQLmanagement.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jianz
 * @since 2022-06-02
 */
@RestController
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private OrderIdProductIdService orderIdProductIdService;

    @PostMapping("/product/addCastOrder")
    public ResultBody addCastOrder(int userid, String productIdList){
        ProductOrder productOrder = new ProductOrder();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        productOrder.setOrdertime(time);
        productOrder.setUserid(userid);
        productOrderService.save(productOrder);

        LambdaQueryWrapper<ProductOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductOrder::getOrdertime,time);
        ProductOrder order = productOrderService.getOne(wrapper);
        Integer orderId = order.getId();
        String[] arr = productIdList.split(",");
        for(int i = 0;i<arr.length;i++){
            OrderIdProductId orderIdProductId = new OrderIdProductId();
            orderIdProductId.setOrderId(orderId);
            orderIdProductId.setProductId(Integer.parseInt(arr[i]));
            orderIdProductIdService.save(orderIdProductId);
        }
        return ResultBody.success("生成订单成功");
    }

    @GetMapping("/product/getOrderList")
    public ResultBody getOrderList(int userid){

        List<MyOrder> myOrders = productOrderService.getMyOrders(userid);
        System.out.println(myOrders);

        return ResultBody.success(myOrders);
    }

}

