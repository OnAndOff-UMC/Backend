package com.onnoff.onnoff.auth.feignClient.client;


import com.onnoff.onnoff.auth.feignClient.config.FeignConfig;
import com.onnoff.onnoff.auth.feignClient.dto.JwkResponse;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "apple-auth-client",url = "https://appleid.apple.com/auth",  configuration = FeignConfig.class)
public interface AppleAuthClient{
    @GetMapping("/keys")
    JwkResponse.JwkSet getKeys();

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    TokenResponse getToken(@RequestBody Map<String, ?> requestBody);

    //회원 탈퇴 메서드
    @GetMapping("/oauth2/v2/revoke")
    void revokeTokens(@RequestBody Map<String, ?> requestBody);
}
