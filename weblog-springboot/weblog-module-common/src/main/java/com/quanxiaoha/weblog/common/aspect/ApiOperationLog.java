package com.quanxiaoha.weblog.common.aspect;

import java.lang.annotation.*;
// 指定注解的保留策略，即注解在何时生效
// RUNTIME，表示注解将在运行是保留，意味着可也通过反射在运行时被访问和解析
@Retention(RetentionPolicy.RUNTIME)

// 指定注解的目标元素，即在哪个地方使用这个注解
@Target({ElementType.METHOD})

// 指定被注解的元素是否会出现在生成的 Java 文档中。
// 如果一个注解使用了该注解，那么在生成文档时，被注解的元素及其注解信息会被包含在文档中。
// 这可以帮助文档生成工具在生成文档时展示关于注解的信息
@Documented
public @interface ApiOperationLog {
    /**
     * API 功能描述
     *
     * @return
     */
    // 表示注解的一个元素（属性），使用该注解时，通过 description = "" 来赋值
    String description() default "";
}
