package com.jianz.SQLmanagement.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/1/20 0:44
 */

@EnableTransactionManagement  // 自动管理事务
@Configuration
@MapperScan("com.jianz.SQLmanagement.dao")
public class MybatisPlusConfig {

    // 注册乐观锁
  /*
    已更新
    @Bean
    public OptimisticLockerInnerInterceptor optimisticInterceptor(){
        return new OptimisticLockerInnerInterceptor();
    }*/
    @Bean
    public MybatisPlusInterceptor myOptimisticLockerInnerInterceptor(){
        MybatisPlusInterceptor interceptor1 = new MybatisPlusInterceptor();
        interceptor1.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor1;
    }
    
     // 分页插件
     @Bean
     public MybatisPlusInterceptor mybatisPlusInterceptor() {
         MybatisPlusInterceptor interceptor2 = new MybatisPlusInterceptor();
         interceptor2.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
         return interceptor2;
     }
}
