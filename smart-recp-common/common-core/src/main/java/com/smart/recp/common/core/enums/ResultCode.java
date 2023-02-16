package com.smart.recp.common.core.enums;

import lombok.Getter;

/**
 * @author ybq
 */
@Getter
public enum ResultCode {
    /**
     * 返回成功
     */
    SUCCESS(20000, "成功"),
    ERROR(40000, "错误"),
    // 用户相关
    UN_LOGIN(40001, "未登录"),
    UN_AUTH(40003, "未授权"),

    /*用户未登录*/
    ACCOUNT_NOT_LOGIN(10001, "用户未登录"),
    /*账号不存在或密码错误*/
    ACCOUNT_LOGIN_ERROR(10002, "账号不存在或密码错误"),
    CODE_LOGIN_ERROR(10005, "验证码错误"),
    /*账号已存在*/
    ACCOUNT_IS_EXISTENT(10003, "账号已存在"),
    TOKEN_ACCESS_FORBIDDEN(40004, "token已被禁止访问"),


    /*账号不存在*/
    ACCOUNT_NOT_EXIST(10004, "账号不存在!"),
    /*账号已禁止  请与管理员联系*/
    USER_ACCOUNT_LOCKED(10005, "账号被锁定, 请与管理员联系"),

    LOGIN_COUNT_LIMIT(10006, "登录失败多次，请稍后在试"),
    /* 参数错误*/
    /*参数不为空*/
    PARAMS_NOT_IS_BLANK(20001, "参数不能为空"),
    /*参数无效*/
    PARAMS_IS_INVALID(20002, "无效参数"),
    /*参数类型错误*/
    PARAM_TYPE_ERROR(20003, "参数类型错误"),
    /*参数缺失*/
    PARAM_IS_DEFICIENCY(20004, "参数缺失"),
    /*暂无权限*/
    PERMISSION_NO_ACCESS(20006, "暂无权限"),
    AUTH_ERROR(20007, "认证失败"),
    /* 业务错误 */

    /* 业务繁忙 请稍后在试 */
    BUSINESS_UNKNOW_ERROR(30001, "业务繁忙 请稍后在试"),
    SYSTEM_ERROR(99999, "接口错误"),

    /* ======系统错误：40001-49999===== */
    /* 提示语 "系统繁忙，请稍后重试"*/
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    /*未知错误 请稍后在试*/
    SYSTEM_UNKNOW_ERROR(40002, "未知错误 请稍后在试"),
    /*内部系统接口调用异常*/
    INNER_INVOKE_ERROR(50001, "内部系统接口调用异常"),
    /*外部系统接口调用异常*/
    OUTER_INVOKE_ERROR(50002, "外部系统接口调用异常"),
    /*该接口禁止访问*/
    NO_ACCESS_FORBIDDEN(50003, "禁止访问"),
    /*接口地址无效*/
    NO_FOUND_ERROR(50004, "接口地址无效"),
    /* 数据错误 */
    DATA_IS_WRONG(60001, "数据错误");
    /**
     * 返回码
     */
    private Integer status;
    /**
     * 返回说明
     */
    private String message;

    ResultCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

}
