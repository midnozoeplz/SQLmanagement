package com.jianz.SQLmanagement.security;

import cn.hutool.json.JSONUtil;
import com.jianz.SQLmanagement.pojo.ResultBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jianz
 * @version 1.0
 * @Description 认证无效的处理器
 * @Email jianz8153.gmail.com
 * @Date 2022/5/22 0:55
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream outputStream = response.getOutputStream();

        ResultBody result = ResultBody.error("请先登录");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
