package com.quanxiaoha.weblog.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenHelper implements InitializingBean {

    // 签发人
    @Value("${jwt.issuer}")
    private String issuer;

    // 密钥
    private Key key;

    // jwt 解析
    private JwtParser jwtParser;

    @Value("${jwt.tokenExpireTime}")
    private Long tokenExpireTime;

    // 解码配置文件中的配置的 Base 64 编码 key 为密钥
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    // 初始化 JwtParser
    @Override
    public void afterPropertiesSet() throws Exception {
        jwtParser = Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(key)
                .setAllowedClockSkewSeconds(10) // 设置能够容忍的最大时钟误差
                .build();
    }

    // 生成 Token
    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        // 设置 token 一小时过期
        LocalDateTime expireTime = now.plusHours(tokenExpireTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // 设置签发时间
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant())) // 设置过期时间
                .signWith(key)
                .compact();
    }

    // 解析token
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    //生成一个 Base64 安全密钥
    private static String generateBase64Key() {
        // 生成安全密钥
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // 将密钥进行 Base64 编码
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        return base64Key;
    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println("key：" + key);
    }

    // 校验 Token 是否可用
    public void validateToken(String token) {
        jwtParser.parseClaimsJws(token);
    }

    // 解析 token 获取用户名
    public String getUsernameByToken(String token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
