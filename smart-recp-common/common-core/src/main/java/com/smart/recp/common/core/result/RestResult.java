package com.smart.recp.common.core.result;


import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author ybq
 */
@Builder
@Getter
public class RestResult<T> {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;


    /**
     * 时间
     */
    private LocalDateTime timestamp;

    public static <T> RestResult<T> success() {
        return success(null);
    }

    public static <T> RestResult<T> success(T data) {
        ResultCode resultCode = ResultCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            resultCode = ResultCode.ERROR;
        }
        return result(resultCode, data);
    }

    public static <T> RestResult<T> error() {
        return error(null);
    }

    public static <T> RestResult<T> error(T data) {
        return result(ResultCode.ERROR, data);
    }

    public static <T> RestResult<T> error(BaseException e) {
        return RestResult.<T>builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> RestResult<T> result(Integer status, String message, T data) {
        return RestResult.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> RestResult<T> result(ResultCode resultCode) {
        return result(resultCode, null);
    }

    public static <T> RestResult<T> result(ResultCode resultCode, T data) {
        return RestResult.<T>builder()
                .status(resultCode.getStatus())
                .message(resultCode.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }


    public static boolean isSuccess(RestResult<?> baseResult) {
        if (baseResult != null && ResultCode.SUCCESS.getStatus().equals(baseResult.getStatus())) {
            return true;
        }
        return false;
    }
}