package com.quanxiaoha.weblog.common.exception;

import com.quanxiaoha.weblog.common.enums.ResponseCodeEnum;
import com.quanxiaoha.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

// 全局异常处理
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler({BizException.class}) // 指定处理什么异常, 这里处理的是 BizException 异常
    @ResponseBody
    public Response<Object> handleBizException(BizException e, HttpServletRequest request) {
        log.warn("{} request fail, errorCode: {}, errorMessage: {}"
                , request.getRequestURI(), e.getErrorCode(), e.getErrorMessage());
        return Response.fail(e);
    }

    //处理系统异常（兜底
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    // 处理参数校验异常
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        // 参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();

        // 获取 BindingResult
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder stringBuilder = new StringBuilder();

        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors -> {
            errors.forEach(error ->
                    stringBuilder.append(error.getField())
                            .append(" ")
                            .append(error.getDefaultMessage())
                            .append("，当前值:'")
                            .append(error.getRejectedValue())
                            .append("';")
            );
        });
        // 错误信息
        String errorMessage = stringBuilder.toString();
        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);
        return Response.fail(errorCode, errorMessage);
    }

    // 处理权限异常
    @ExceptionHandler({AccessDeniedException.class})
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 捕获到鉴权失败异常，主动抛出，交给 RestAccessDeniedHandler 去处理
        log.info("============= 捕获到 AccessDeniedException");
        throw e;
    }
}
