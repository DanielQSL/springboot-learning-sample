package com.qsl.spring.core.filter_and_interceptor;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日志过滤器
 *
 * @author qianshuailong
 * @date 2020/11/28
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "LogFilter")
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        log.info("LogFilter Print Log: {} -> {}",
                ((HttpServletRequest) request).getRequestURI(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void destroy() {

    }
}
