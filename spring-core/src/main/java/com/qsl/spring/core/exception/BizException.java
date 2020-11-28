package com.qsl.spring.core.exception;

import com.qsl.spring.core.common.BaseCommonError;

/**
 * 自定义业务异常
 *
 * @author DanielQSL
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 4564124491192825748L;

    private int code;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.setCode(code);
    }

    public BizException(BaseCommonError commonError) {
        this(commonError.getErrorCode(), commonError.getErrorMsg());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}