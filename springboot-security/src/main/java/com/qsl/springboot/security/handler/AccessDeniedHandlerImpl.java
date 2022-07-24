package com.qsl.springboot.security.handler;

import com.qsl.project.base.model.CommonResponse;
import com.qsl.springboot.security.utils.JsonUtil;
import com.qsl.springboot.security.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权异常处理
 *
 * @author DanielQSL
 */
@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("{} 权限不足", request.getRequestURI(), accessDeniedException);
        final CommonResponse<Object> result = CommonResponse.fail(HttpStatus.FORBIDDEN.value(), "权限不足");
        WebUtils.generateResponse(response, JsonUtil.toJsonString(result));
    }

}
