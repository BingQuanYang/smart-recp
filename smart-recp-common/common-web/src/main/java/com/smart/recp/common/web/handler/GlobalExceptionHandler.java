package com.smart.recp.common.web.handler;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author ybq
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理参数验证的错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RestResult<Object> bindException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        // 获取所有的错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        // 输出
        allErrors.forEach(e -> log.error(e.getDefaultMessage()));
        return RestResult.result(ResultCode.PARAMS_IS_INVALID.getStatus(), allErrors.get(0).getDefaultMessage(), null);
    }

    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    public RestResult<Object> BaseExceptionHandler(BaseException e) {
        return RestResult.error(e);
    }

    /**
     * 未知异常错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public RestResult<Object> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        return RestResult.error(ResultCode.ERROR);
    }
}
