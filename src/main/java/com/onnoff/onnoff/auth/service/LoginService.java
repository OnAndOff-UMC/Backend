package com.onnoff.onnoff.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onnoff.onnoff.auth.feignClient.client.KakaoApiClient;
import com.onnoff.onnoff.auth.feignClient.client.KakaoOauth2Client;
import com.onnoff.onnoff.auth.feignClient.dto.KakaoOauth2DTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final KakaoOauth2Client kakaoOauth2Client;
    private final KakaoApiClient kakaoApiClient;

    /*
        테스트 용으로 만든거, 실제로는 프론트에서 처리해서 액세스 토큰만 가져다 줌
    */
    public String getAccessToken(String code){
        KakaoOauth2DTO.TokenResponseDTO tokenResponseDTO = kakaoOauth2Client.getAccessToken("authorization_code",
                "32c0787d1b1e9fcabcc24af247903ba8",
                "http://localhost:8080/oauth2/login/kakao",
                code);
        return tokenResponseDTO.getAccessToken();
    }
    /*
        토큰 유효성 검증, 유효하지 않으면 예외를 발생시키도록 처리, 예외는 CustomErrorDecoder에서 처리
     */
    public void validate(String accessToken){
        String cleanedAccessToken = cleanAccessToken(accessToken);
        kakaoApiClient.getTokenValidate(cleanedAccessToken);
    }

    private String cleanAccessToken(String accessToken){
        accessToken = accessToken.replaceAll("[\u0000-\u001F\u007F-\u00FF:]", ""); // 헤더에 있으면 안되는 값 대체
        accessToken = accessToken.trim(); // 앞뒤 공백 제거
        accessToken = "Bearer " + accessToken; // 토큰 기반 인증 형식
        return accessToken;
    }
     /*
        토큰으로 유저정보를 가져오는 메서드
     */
    public KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(String accessToken) throws JsonProcessingException {
        String cleanedAccessToken = cleanAccessToken(accessToken);
        String emailProperty = "kakao_account.email";
        String nameProperty = "kakao_account.name";
        List<String> propertyKeysList = Arrays.asList(emailProperty, nameProperty);

        // List -> JSON 형식으로 바꾸어 전달
        ObjectMapper objectMapper = new ObjectMapper();
        String propertyKeys = objectMapper.writeValueAsString(propertyKeysList);
        KakaoOauth2DTO.UserInfoResponseDTO userInfoResponseDTO = kakaoApiClient.getUserInfo(cleanedAccessToken, propertyKeys);
        return userInfoResponseDTO;
    }
}
