package com.qsl.springboot.security.utils;

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
     *
     * @param response HttpServletResponse
     * @param result   响应结果
     * @throws IOException IO异常
     */
    public static void generateResponse(HttpServletResponse response, String result)
            throws IOException {
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/json;charset=UTF-8");
            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

}
