package com.jianz.SQLmanagement.security;

import com.alibaba.fastjson.JSON;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jianz
 * @version 1.0
 * @Description SpringSecurity处理异常的handler
 * @Email jianz8153.gmail.com
 * @Date 2022/5/28 0:12
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResultBody error = ResultBody.error(HttpStatus.UNAUTHORIZED.toString(), "用户认证失败请查询登录");
        String json = JSON.toJSONString(error);
        //处理异常
        WebUtils.renderString(httpServletResponse,json);
    }
}
