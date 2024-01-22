package com.onnoff.onnoff.auth.feignClient.client;

import com.onnoff.onnoff.auth.feignClient.config.FeignConfig;
import com.onnoff.onnoff.auth.feignClient.dto.KakaoOauth2DTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/*
 토큰 유효성 검증하고 사용자 정보 가져오는 client
 */

@FeignClient(name = "kakao-api-client", url = "https://kapi.kakao.com", configuration = FeignConfig.class)
public interface KakaoApiClient {
    @GetMapping("v1/user/access_token_info")
    KakaoOauth2DTO.TokenValidateResponseDTO getTokenValidate(@RequestHeader("Authorization") String accessToken);
    @GetMapping(value = "/v2/user/me")
    KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(@RequestHeader("Authorization") String accessToken, @RequestParam(name = "property_keys") String propertyKeys);

}
