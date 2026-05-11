package com.wwc.project.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        // 1. 從 Cookie 中提取 token
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 2. 驗證 Token
        if (token != null) {
//            try {
                Claims claims = jwtUtil.parseToken(token);
                String username = claims.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 建立認證物件 (目前沒實作角色權限，所以傳空 List)
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 將認證資訊存入上下文
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
//            }
//            catch (Exception e) {
//                // Token 無效或過期，清空 Context 確保安全
//                SecurityContextHolder.clearContext();
//                logger.error("JWT validation failed: " + e.getMessage());
//
//                // 1. 設定 401 狀態碼
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                // 2. 設定回傳格式
//                response.setContentType("application/json;charset=UTF-8");
//                response.getWriter().write("{\"message\": \"Invalid or expired token\"}");
//                return;
//            }
        }

        filterChain.doFilter(request, response);
    }
}