package com.qsl.spring.core.common;

/**
 * 通用错误接口
 *
 * @author DanielQSL
 */
public interface BaseCommonError {

    int getErrorCode();

    String getErrorMsg();

    BaseCommonError setErrorMsg(String errorMsg);
}
