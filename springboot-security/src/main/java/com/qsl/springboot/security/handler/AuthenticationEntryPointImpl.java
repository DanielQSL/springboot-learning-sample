package com.qsl.springboot.security.handler;

import com.qsl.project.base.model.CommonResponse;
import com.qsl.springboot.security.utils.JsonUtil;
import com.qsl.springboot.security.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理
 *
 * @author DanielQSL
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final CommonResponse<Object> result = CommonResponse.fail(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
        WebUtils.generateResponse(response, JsonUtil.toJsonString(result));
    }
}
