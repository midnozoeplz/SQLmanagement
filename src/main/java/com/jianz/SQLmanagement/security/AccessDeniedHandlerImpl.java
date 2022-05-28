package com.jianz.SQLmanagement.security;

import com.alibaba.fastjson.JSON;
import com.jianz.SQLmanagement.pojo.ResultBody;
import com.jianz.SQLmanagement.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jianz
 * @version 1.0
 * @Description
 * @Email jianz8153.gmail.com
 * @Date 2022/5/28 0:23
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResultBody error = ResultBody.error(HttpStatus.FORBIDDEN.toString(), "您的权限不足");
        String json = JSON.toJSONString(error);
        //处理异常
        WebUtils.renderString(httpServletResponse,json);
    }
}
