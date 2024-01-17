package com.onnoff.onnoff.auth.client;

import com.onnoff.onnoff.auth.client.dto.KakaoOauth2DTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 토큰 유효성 검증하고 사용자 정보 가져오는 client
 */
@FeignClient(name = "kakao-api-client", url = "https://kapi.kakao.com")
public interface KakaoApiClient {
    @GetMapping("v1/user/access_token_info")
    KakaoOauth2DTO.TokenValidateResponseDTO getTokenValidate(@RequestHeader("Authorization") String accessToken);
    @PostMapping(value = "/v2/user/me")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(@RequestHeader("Authorization") String accessToken, @RequestParam(name = "property_keys") String propertyKeys);

}
