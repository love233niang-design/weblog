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
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),
    FORBIDDEN("20004", "演示账号仅支持查询操作！"),
    USERNAME_NOT_FOUND("20003", "该用户不存在"),
    CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！"),


    ;

    // 异常码
    private String errorCode;
    // 异常信息
    private String errorMessage;

}
