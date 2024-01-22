package com.onnoff.onnoff.auth.jwt.filter;

import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.auth.jwt.service.JwtUtil;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 토큰 파싱해서 id 얻어오기
        String accessToken = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        try{
            // 얻은 아이디로 유저 조회하기
            String userId = jwtUtil.getUserId(accessToken);
            User user = userService.getUser(Long.valueOf(userId));

            // 조회한 유저 ThreadLocal에 저장하기
            UserContext.setUser(user);
            return true;
        }
        catch (IllegalArgumentException exception){
            // jwt 필터에서 검증된 토큰이라 예외 안나겠지만 없지만 혹시 모르니
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 요청 작업 끝나면 ThreadLocal 자원 해제
        UserContext.clearUser();
    }
}
