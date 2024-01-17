package com.onnoff.onnoff.auth.client;


import com.onnoff.onnoff.auth.client.dto.KakaoOauth2DTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "kakao-oauth2-client", url = "https://kauth.kakao.com")
public interface KakaoOauth2Client {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @PostMapping("/oauth/token")
    KakaoOauth2DTO.TokenResponseDTO getAccessToken(@RequestParam(name = "grant_type") String grantType,
                                                   @RequestParam(name = "client_id") String clientId,
                                                   @RequestParam(name = "redirect_uri") String redirectUri,
                                                   @RequestParam(name = "code") String code
    );

}
