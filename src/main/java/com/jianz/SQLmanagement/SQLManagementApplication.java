package com.jianz.SQLmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 13:33
 */
@SpringBootApplication
@ServletComponentScan("com.jianz.SQLmanagement")
public class SQLManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SQLManagementApplication.class,args);
    }
}
