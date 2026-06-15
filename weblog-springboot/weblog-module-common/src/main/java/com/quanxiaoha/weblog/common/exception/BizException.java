package com.quanxiaoha.weblog.common.exception;

import lombok.Data;

// 自定义业务异常
@Data
public class BizException extends RuntimeException{
    // 异常码
    private String errorCode;
    // 异常信息
    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
