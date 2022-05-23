package com.jianz.SQLmanagement;

import com.jianz.SQLmanagement.utils.JwtUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/23 0:13
 */
@SpringBootTest
public class test {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public  void main() {
            String s = jwtUtils.generateToken("123");
            System.out.println(s);
    }
}
