package com.quanxiaoha.weblog.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 密码加密
@Configuration
public class PasswordEncoderConfig {
    // 定义了密码加密和密码验证的方法，通过这个接口，可将密码加密为不可逆的哈希值
    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCrypt 是一种安全且适合密码存储的哈希算法，进行哈希加密时会自动加”盐“，提高密码安全性
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("hq"));
    }
}
