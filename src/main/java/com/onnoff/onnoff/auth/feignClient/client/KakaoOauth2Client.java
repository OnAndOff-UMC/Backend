package com.onnoff.onnoff.auth.feignClient.client;


import com.onnoff.onnoff.auth.feignClient.config.FeignConfig;
import com.onnoff.onnoff.auth.feignClient.dto.JwkResponse;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.KakaoOauth2DTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "kakao-oauth2-client", url = "https://kauth.kakao.com", configuration = FeignConfig.class)
public interface KakaoOauth2Client {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @PostMapping("/oauth/token")
    TokenResponse getAccessToken(@RequestParam(name = "grant_type") String grantType,
                                 @RequestParam(name = "client_id") String clientId,
                                 @RequestParam(name = "redirect_uri") String redirectUri,
                                 @RequestParam(name = "code") String code
    );
    @GetMapping("/.well-known/jwks.json")
    JwkResponse.JwkSet getKeys();
}
