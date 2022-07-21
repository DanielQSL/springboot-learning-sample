package com.qsl.springboot.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 *
 * @author DanielQSL
 */
@Component
public class JwtUtils {

    /**
     * 签发者
     */
    public static final String JWT_ISSUER = "qsl";

    /**
     * token有效期，默认为一周 (ms)
     */
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7L;

    /**
     * 密钥 64个字符
     */
    @Value("${jwt.secretKey}")
    private String secretKey;

    /**
     * 生成 JWT token
     *
     * @param subject 主题  可以是JSON数据
     * @return token
     */
    public String generateToken(String subject) {
        return generateToken(UUID.randomUUID().toString(), subject);
    }

    /**
     * 生成 JWT token
     *
     * @param uuid    唯一标识
     * @param subject 主题  可以是JSON数据
     * @return token
     */
    public String generateToken(String uuid, String subject) {
        return Jwts.builder()
                // JWT的唯一标识
                .setId(uuid)
                // 主题  可以是JSON数据
                .setSubject(subject)
                // 签发者
                .setIssuer(JWT_ISSUER)
                // 签发时间
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 生成加密后的秘钥
     */
    private SecretKey generateKey() {
        byte[] encodedKey = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
    }

    /**
     * 解析JWT Token
     *
     * @param token JWT token
     * @return 数据
     */
    public Claims parseJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从JWT Token中获取主题信息
     *
     * @param token JWT token
     * @return 主题信息
     */
    public String getSubjectFromToken(String token) {
        Claims claims = parseJwtToken(token);
        return claims.getSubject();
    }

    /**
     * 从JWT Token中获取用户信息
     *
     * @param token JWT token
     * @param clazz 返回类型
     * @param <T>   类型
     * @return 用户信息
     */
    public <T> T getUserInfoFromToken(String token, Class<T> clazz) {
        Claims claims = parseJwtToken(token);
        return JsonUtil.parseObject(claims.getSubject(), clazz);
    }

    /**
     * 判断 token 是否有效
     *
     * @param token JWT token
     * @return 是否有效
     */
    public boolean isTokenExpired(String token) {
        Claims claims = parseJwtToken(token);
        return claims.getExpiration().after(new Date());
    }

}
