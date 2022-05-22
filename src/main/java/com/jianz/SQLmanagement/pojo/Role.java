package com.jianz.SQLmanagement.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Jianz
 * @version 1.0
 * @Description 角色类
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 15:56
 */
@Data
@Accessors(chain = true)  //开启链式编程
@EqualsAndHashCode(callSuper = true) //不排除父类字段
public class Role extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String name;

    private String code;

    private String remark;

}
