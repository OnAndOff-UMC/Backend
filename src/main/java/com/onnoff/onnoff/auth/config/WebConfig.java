package com.onnoff.onnoff.auth.config;


import com.onnoff.onnoff.auth.jwt.filter.JwtAuthFilter;
import com.onnoff.onnoff.auth.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtAuthFilter(jwtTokenProvider)); // 필터 인스턴스 설정
        registration.addUrlPatterns("/*");
        registration.setOrder(1); // 필터의 순서 설정. 값이 낮을수록 먼저 실행됨
        return registration;
    }
}
