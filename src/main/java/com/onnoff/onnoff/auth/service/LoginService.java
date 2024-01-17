package com.onnoff.onnoff.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.auth.client.KakaoApiClient;
import com.onnoff.onnoff.auth.client.KakaoOauth2Client;
import com.onnoff.onnoff.auth.client.dto.KakaoOauth2DTO;
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
    public void getAccessToken(String code){
        KakaoOauth2DTO.TokenResponseDTO tokenResponseDTO = kakaoOauth2Client.getAccessToken("authorization_code",
                "32c0787d1b1e9fcabcc24af247903ba8",
                "http://localhost:8080/oauth2/login/kakao",
                code);
        log.info("token = {}", tokenResponseDTO.getAccessToken());
    }
    /*
        토큰 유효성 검증, 유효하지 않으면 예외를 뱉도록, 아직 예외 처리는 구현 안됨
     */
    public ResponseEntity<String> validate(String accessToken){
        try{
            KakaoOauth2DTO.TokenValidateResponseDTO responseDTO = kakaoApiClient.getTokenValidate(accessToken);
            log.info("validate response = {}", responseDTO);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

     /*
        토큰으로 유저정보를 가져오는 메서드
     */

    public KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(String accessToken) throws JsonProcessingException {
        String emailProperty = "kakao_account.email";
        String nameProperty = "kakao_account.name";
        List<String> propertyKeysList = Arrays.asList(emailProperty, nameProperty);

        // List -> JSON 형식으로 바꾸어 전달
        ObjectMapper objectMapper = new ObjectMapper();
        String propertyKeys = objectMapper.writeValueAsString(propertyKeysList);
        KakaoOauth2DTO.UserInfoResponseDTO userInfoResponseDTO = kakaoApiClient.getUserInfo(accessToken, propertyKeys);
        log.info("userInfoResponseDTO = {}", userInfoResponseDTO);
        return userInfoResponseDTO;
    }
}
