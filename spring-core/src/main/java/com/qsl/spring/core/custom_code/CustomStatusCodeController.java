package com.qsl.spring.core.custom_code;

import com.qsl.spring.core.common.ServerResponse;
import com.qsl.spring.core.exception.BadRequestException;
import com.qsl.spring.core.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义状态码返回
 *
 * @author qianshuailong
 * @date 2020/11/28
 */
@RequestMapping("/custom")
@RestController
public class CustomStatusCodeController {

    /**
     * 第一种方式自定义返回状态码
     * 使用ResponseEntity类：标识整个HTTP响应（状态码、头部信息、响应体）
     *
     * @return 响应结果
     */
    @GetMapping("/first")
    public ResponseEntity<ServerResponse<String>> first() {
        ServerResponse<String> response = ServerResponse.success("哈哈");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 异常类或Controller方法上标识@ResponseStatus注解
     *
     * @return 响应结果
     */
    @GetMapping("/second")
    public ServerResponse<String> second() {
        // 业务逻辑
        throw new BadRequestException();
    }

    /**
     * 第三种方式自定义返回状态码
     * 应用：自定义特殊页面、重定向使用
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "current http request 404")
    @GetMapping("/third")
    public void response404() {

    }

    /**
     * 第四种方式自定义返回状态码
     * RestControllerAdvice类
     */
    @GetMapping("/fourth")
    public ServerResponse<String> fourth() throws BizException {
        // 业务逻辑
        throw new BizException("some error");
    }

}
