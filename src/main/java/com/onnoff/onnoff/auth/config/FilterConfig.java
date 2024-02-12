package com.onnoff.onnoff.auth.config;

import com.onnoff.onnoff.auth.jwt.filter.JwtAuthFilter;
import com.onnoff.onnoff.auth.jwt.filter.UserInterceptor;
import com.onnoff.onnoff.auth.jwt.service.JwtUtil;
import com.onnoff.onnoff.auth.jwt.service.TokenProvider;
import com.onnoff.onnoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(tokenProvider);
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor(userService, jwtUtil);
    }
}