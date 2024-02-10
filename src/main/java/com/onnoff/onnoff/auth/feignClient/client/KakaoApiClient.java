package com.onnoff.onnoff.auth.feignClient.client;

import com.onnoff.onnoff.auth.feignClient.config.FeignConfig;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.KakaoOauth2DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
 토큰 유효성 검증 하고 사용자 정보 가져오는 client
 */

@FeignClient(name = "kakao-api-client", url = "https://kapi.kakao.com", configuration = FeignConfig.class)
public interface KakaoApiClient {
    @GetMapping("v1/user/access_token_info")
    KakaoOauth2DTO.TokenValidateResponseDTO getTokenValidate(@RequestHeader("Authorization") String accessToken);
    @GetMapping(value = "/v1/oidc/userinfo")
    KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(@RequestHeader("Authorization") String accessToken);
    @PostMapping(value = "/v1/user/unlink", consumes = "application/x-www-form-urlencoded")
    ResponseEntity unlink(@RequestHeader("Authorization") String adminKey, @RequestBody Map<String, ?> requestBody);

}
