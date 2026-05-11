package com.wwc.project.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 1. 密鑰長度必須至少 256-bit (即 32 個字元)，否則會報錯
    @Value("${jwt.secret}")
    private String secretString;

    @Value("${jwt.expireTime}")
    private long expireTime;


    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretString.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // 解析成功代表簽章正確且未過期
        } catch (Exception e) {
            // 可能是 ExpiredJwtException (過期)
            // 或是 SignatureException (簽章不符/被竄改)
            // 或是 MalformedJwtException (格式錯誤)
            return false;
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 4. 解析時也要用同一個 key 物件
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}