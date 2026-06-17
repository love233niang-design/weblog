package com.quanxiaoha.weblog.admin.config;

import com.quanxiaoha.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .mvcMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源-
//                .anyRequest().permitAll().and() // 其他都需要放行，无需认证
//                .formLogin().and() // 使用表单
//                .httpBasic(); // 使用 HTTP basic 认证


        http.csrf().disable() // 禁用 CSRF 跨站请求伪造
                .formLogin().disable() // 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig)// 设置用户登录认证相关配置
                .and()
                .authorizeHttpRequests()
                .mvcMatchers("/admin/**").authenticated()// 认证所有以 /admin 为前缀的 URL 资源-
                .anyRequest().permitAll()// 其他都需要放行，无需认证
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// 前后端分离，无需创建

        // Http Basic 认证：当客户端发送请求时，会将用户名和密码使用 Base64 编码的形式放在请求头中，
        // 服务器接收到请求后会解码并验证这些消息
    }
}
