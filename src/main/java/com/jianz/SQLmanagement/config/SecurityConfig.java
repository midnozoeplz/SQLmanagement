package com.jianz.SQLmanagement.config;

import com.jianz.SQLmanagement.filter.JwtAuthenticationTokenFilter;
import com.jianz.SQLmanagement.security.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 17:10
 */
@Configuration
@EnableWebSecurity
//开启权限注解，且在请求之前进行校验
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //自定义加密方式
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //白名单
    private static final String[] URL_WHITELIST = {
            "/download",
            "/upload",
            "/text/new",
            "/text/old",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagge‌​r-ui.html", //swaager的配置资源
            "/favicon.ico",
            "/user/register",
            "/user/login"
    };

//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
//        builder.userDetailsService(myUserDetailService);
//    }

    @Test
    public void test(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.matches("123456", "$2a$10$LDSSHqs2blvKCFm63/m4/OvymQbuBY9AnYWi/Jxrls96OGI5cKx3a"));
//        String encode = passwordEncoder.encode("admin");
//        String encode1 = passwordEncoder.encode("123456");
//
//        System.out.println(encode);
//        System.out.println(encode1);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                // 禁用session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()//释放白名单
                .anyRequest().authenticated()

                // 配置自定义的过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)

                // 配置异常处理器
                .exceptionHandling()
                //配置认证失败处理器
            //    .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                //允许跨域
                .and().cors()
        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
