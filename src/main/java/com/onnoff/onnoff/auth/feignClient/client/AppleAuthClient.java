package com.onnoff.onnoff.auth.feignClient.client;


import com.onnoff.onnoff.auth.feignClient.dto.JwkResponse;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-auth-client",url = "https://appleid.apple.com/auth")
public interface AppleAuthClient{
    @GetMapping("/keys")
    JwkResponse.JwkSet getKeys();

    @GetMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    TokenResponse getToken(MultiValueMap requestBody);

    //회원 탈퇴 메서드
//    @GetMapping("/revoke")
//    KakaoOauth2DTO.TokenValidateResponseDTO getTokenValidate(@RequestHeader("Authorization") String accessToken);
}
