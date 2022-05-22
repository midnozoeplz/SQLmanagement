package com.jianz.SQLmanagement.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 16:10
 */
@Data
@Accessors(chain = true)
public class UserRole {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private Integer rid;
}
