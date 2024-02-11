package com.onnoff.onnoff.auth.config;


import com.onnoff.onnoff.auth.jwt.filter.JwtAuthFilter;
import com.onnoff.onnoff.auth.jwt.filter.UserInterceptor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtAuthFilter jwtAuthFilter;
    private final EntityManagerFactory entityManagerFactory;
    private final UserInterceptor userInterceptor;
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtAuthFilter); // 필터 인스턴스 설정
        registration.addUrlPatterns("/*"); //서블릿 컨택스트에서 /*는 모든 요청, /**는 인식되지 않음
        registration.setOrder(1); // 필터의 순서 설정. 값이 낮을수록 먼저 실행
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor);

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**") // 스프링 경로는 /*와 /**이 다름
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**", "/oauth2/**", "/health", "/token/**" ,
                        "/message/**", "/enums/**", "/users/nickname");
    }
}
