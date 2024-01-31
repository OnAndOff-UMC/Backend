package com.onnoff.onnoff.auth.service;

import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;

public interface LoginService {
    default String cleanToken(String token){
        token = token.replaceAll("[\u0000-\u001F\u007F-\u00FF:]", ""); // 헤더에 있으면 안되는 값 대체
        token = token.trim(); // 앞뒤 공백 제거
        return token;
    }
    public TokenResponse getAccessTokenByCode(String code);
    public void validate(String accessToken);
}
