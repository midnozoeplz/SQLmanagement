package com.jianz.SQLmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianz
 * @since 2022-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_id_Product_id")
@ApiModel(value="OrderIdProductId对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class OrderIdProductId implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer productId;


}
