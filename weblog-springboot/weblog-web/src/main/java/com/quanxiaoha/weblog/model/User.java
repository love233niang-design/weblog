package com.quanxiaoha.weblog.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// 使用 JSR 380 参数校验注解
// 可自动校验字段格式
@Data
@ApiModel(value = "用户实体类") //描述类
public class User {
    // 用户名
    @NotBlank(message = "用户名不能为空")// 验证字符串不能为空
    @ApiModelProperty(value = "用户名") // 描述字段
    private String username;
    // 性别
    @NotNull(message = "性别不能为空")// 验证数字不能为空
    private Integer sex;

    // 年龄
    @NotNull(message = "年龄不能为空")// 验证字段不能为空
    @Min(value = 18, message = "年龄不能小于18")// 验证字段大于最小值
    @Max(value = 100, message = "年龄不能大于100")// 验证字段小于最大值
    private Integer age;

    // 邮箱
    @NotBlank(message = "邮箱不能为空")// 验证字符串不能为空
    @Email(message = "邮箱格式不正确")// 验证邮箱格式
    private String email;

    // 创建时间
    private LocalDateTime createTime;
    // 更新日期
    private LocalDate updateDate;
    // 时间
    private LocalTime time;
}
