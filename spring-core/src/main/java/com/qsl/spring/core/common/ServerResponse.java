package com.qsl.spring.core.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 服务端返回的结果集
 *
 * @author DanielQSL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ServerResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    /**
     * 空参构造函数
     * 注：Jackson反序列化需要无参构造函数
     */
    public ServerResponse() {
    }

    private ServerResponse(int code) {
        this.code = code;
    }

    private ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> success() {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }

    public static <T> ServerResponse<T> successMsg(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> success(String msg, T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> fail() {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> fail(String errorMessage) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> fail(BaseCommonError commonError) {
        return new ServerResponse<>(commonError.getErrorCode(), commonError.getErrorMsg());
    }

    public static <T> ServerResponse<T> fail(int errorCode, String errorMessage) {
        return new ServerResponse<>(errorCode, errorMessage);
    }
}
