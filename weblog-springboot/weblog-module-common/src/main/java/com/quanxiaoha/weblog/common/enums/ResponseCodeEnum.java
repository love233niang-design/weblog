package com.quanxiaoha.weblog.common.enums;

import com.quanxiaoha.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // 通用异常状态码
    SYSTEM_ERROR("1000", "出错了"),
    // 业务异常状态码
    PRODUCT_NOT_FOUND("2000", "商品不存在"),
    ;

    // 异常码
    private String errorCode;
    // 异常信息
    private String errorMessage;

}
