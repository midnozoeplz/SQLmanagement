package com.jianz.SQLmanagement.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/6/2 18:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MyOrder {
    private Integer id;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private Integer num;

    private Double price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime orderTime;

}
