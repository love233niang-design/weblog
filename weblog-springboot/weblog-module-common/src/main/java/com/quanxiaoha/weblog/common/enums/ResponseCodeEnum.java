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
    PRODUCT_NOT_FOUND("1001", "商品不存在"),
    PARAM_NOT_VALID("1002", "参数不合法"),

    LOGIN_FAIL("20000", "登录失败"),
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),

    ;

    // 异常码
    private String errorCode;
    // 异常信息
    private String errorMessage;

}
