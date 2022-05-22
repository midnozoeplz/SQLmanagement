package com.jianz.SQLmanagement.config;

import com.jianz.SQLmanagement.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/21 17:10
 */
@Configuration
@EnableWebSecurity
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
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;



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
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
            "/sys/user/register",//注册接口
            "/location/map",//定位接口
            "/delay",
            "/sys/register/list",
            "/sys/register/registering",
            "/sys/register/finishall",
            "/sys/register/clear",
            "/sys/register/email",
            "/sys/register/report",
            "/sys/register/dead",
            "/getZipFile",
            "/mqtt",
            "/sys/file/loginbg",
            "/sys/file/homebg",
            "/register"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(myUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决跨域问题，禁用csrf功能
       http //.cors().and().csrf().disable()

               // 登录配置
               .formLogin()//自定义登陆，采用表单的形式
               .successHandler(loginSuccessHandler)
               .failureHandler(loginFailureHandler)

               .and()
               .logout()
               .logoutSuccessHandler(jwtLogoutSuccessHandler)

               // 禁用session
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

               // 配置拦截规则
               .and()
               .authorizeRequests()
               .antMatchers(URL_WHITELIST).permitAll()//释放白名单
               .anyRequest().authenticated()

               // 异常处理器
               .and()
               .exceptionHandling()
               .authenticationEntryPoint(jwtAuthenticationEntryPoint)
               .accessDeniedHandler(jwtAccessDeniedHandler)

       ;
    }


}
