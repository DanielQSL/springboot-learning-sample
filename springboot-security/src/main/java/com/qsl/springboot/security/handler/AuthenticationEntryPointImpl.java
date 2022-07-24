package com.qsl.springboot.security.handler;

import com.qsl.project.base.model.CommonResponse;
import com.qsl.springboot.security.utils.JsonUtil;
import com.qsl.springboot.security.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("{} 认证失败", request.getRequestURI(), authException);
        final CommonResponse<Object> result = CommonResponse.fail(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.getWriter().println(JsonUtil.toJsonString(result));
        WebUtils.generateResponse(response, JsonUtil.toJsonString(result));
    }

}
