package com.quanxiaoha.weblog.common.exception;

import com.quanxiaoha.weblog.common.enums.ResponseCodeEnum;
import com.quanxiaoha.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

// 全局异常处理
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BizException.class}) // 指定处理什么异常, 这里处理的是 BizException 异常
    @ResponseBody
    public Response<Object> handleBizException(BizException e, HttpServletRequest request) {
        log.warn("{} request fail, errorCode: {}, errorMessage: {}"
                , request.getRequestURI(), e.getErrorCode(), e.getErrorMessage());
        return Response.fail(e);
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }
}
