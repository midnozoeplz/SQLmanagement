package com.jianz.SQLmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2022-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserProduct对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class UserProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "商品id")
    private Integer productId;

    @ApiModelProperty(value = "商品名")
    private String productName;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "购买数量")
    private Integer num;


}
