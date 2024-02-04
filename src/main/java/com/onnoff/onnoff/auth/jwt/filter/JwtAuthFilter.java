
package com.onnoff.onnoff.auth.jwt.filter;


/**
    header에서 액세스 토큰 가져와 검증
    유효하지 않으면 예외 발생
 */


import com.onnoff.onnoff.auth.jwt.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final static String[] ignorePrefix = {"/swagger-ui", "/v3/api-docs", "/oauth2", "/health", "/token/validate" , "/message", "/enums"};
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("url ={}", request.getRequestURI());
        // 지정된 Path는 건너뛰기
        String currentPath = request.getServletPath();
        for(String ignorePath: ignorePrefix){
            if ( currentPath.startsWith(ignorePath) ) {
                doFilter(request, response, filterChain);
                return;
            }
        }
        String accessToken = null;
        String authHeader = request.getHeader("Authorization");
        // AccessToken 추출하여 검증
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        if (jwtTokenProvider.verifyToken(accessToken)){
            log.info("인증성공");
            filterChain.doFilter(request, response);
        }
        else{
            log.info("인증실패");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
