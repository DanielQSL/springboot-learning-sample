package com.qsl.spring.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author qianshuailong
 * @date 2020/11/28
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "This is second way to set http status code")
public class BadRequestException extends RuntimeException {
}
