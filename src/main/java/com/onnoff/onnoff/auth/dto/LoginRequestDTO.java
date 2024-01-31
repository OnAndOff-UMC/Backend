package com.onnoff.onnoff.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class LoginRequestDTO {
    @Getter
    public static class AppleTokenValidateDTO{
        @JsonProperty("user")
        private String oauthId;
        @JsonProperty("full_name")
        private String fullName;
        private String email;
        @JsonProperty("identity_token")
        private String identityToken;
        @JsonProperty("authorization_code")
        private String authorizationCode;
    }
    @Getter
    public static class KakaoTokenValidateDTO{
        @JsonProperty("identity_token")
        private String identityToken;
        @JsonProperty("access_token")
        private String accessToken;
    }
}
