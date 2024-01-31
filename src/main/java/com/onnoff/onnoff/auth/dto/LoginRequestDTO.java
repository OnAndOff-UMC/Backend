package com.onnoff.onnoff.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class LoginRequestDTO {

    @Getter
    public static class AppleTokenValidateDTO{
        private String oauthId;
        private String fullName;
        private String email;
        private String identityToken;
        private String authorizationCode;
    }
    @Getter
    public static class KakaoTokenValidateDTO{
        private String identityToken;
        private String accessToken;
    }
}
