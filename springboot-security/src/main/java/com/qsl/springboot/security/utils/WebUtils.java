package com.qsl.springboot.security.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Web工具类
 *
 * @author DanielQSL
 */
public class WebUtils {

    private WebUtils() {
    }

    /**
     * 生成响应结果
     * 另外一种写法：response.getWriter().println(str);
     *
     * @param response HttpServletResponse
     * @param result   响应结果
     * @throws IOException IO异常
     */
    public static void generateResponse(HttpServletResponse response, String result)
            throws IOException {
        generateResponse(response, HttpStatus.OK, result);
    }

    /**
     * 生成响应结果
     *
     * @param response   HttpServletResponse
     * @param httpStatus HTTP状态码
     * @param result     响应结果
     * @throws IOException IO异常
     */
    public static void generateResponse(HttpServletResponse response, HttpStatus httpStatus, String result)
            throws IOException {
        try (OutputStream out = response.getOutputStream()) {
            response.setStatus(httpStatus.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

}
