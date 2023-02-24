package com.smart.recp.auth.handler;

import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ybq
 */
@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    /**
     * 用户名和密码异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidGrantException.class)
    public RestResult handleInvalidGrantException(InvalidGrantException e) {
        return RestResult.error(ResultCode.ACCOUNT_LOGIN_ERROR);
    }


    /**
     * 账户异常(禁用、锁定、过期)
     *
     * @param e
     * @return
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public RestResult handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return RestResult.error(e.getMessage());
    }

}