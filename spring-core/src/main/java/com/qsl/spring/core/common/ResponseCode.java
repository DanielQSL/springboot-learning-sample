package com.qsl.spring.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 相应业务码
 * 业务码设计：前三位代表项目，中间两位代表开发小组，后三位错误码
 * （根据实际来设计自己项目对应的业务码，但是最好设计的有一定含义）
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum ResponseCode implements BaseCommonError {
    /**
     * 响应码
     */
    SUCCESS(0, "SUCCESS"),
    ERROR(-1, "ERROR"),
    PARAM_ILLEGAL(10011001, "参数不合法"),
    ;

    private int code;
    private String desc;

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.desc;
    }

    @Override
    public BaseCommonError setErrorMsg(String errorMsg) {
        this.desc = errorMsg;
        return this;
    }
}
