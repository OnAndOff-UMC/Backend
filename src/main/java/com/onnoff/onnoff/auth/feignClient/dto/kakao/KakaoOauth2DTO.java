package com.onnoff.onnoff.auth.feignClient.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;


public class KakaoOauth2DTO {

    @Getter
    public static class TokenValidateResponseDTO{
        private Long id;
        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonProperty("app_id")
        private Integer appId;
    }

    @Getter
    @ToString
    public static class UserInfoResponseDTO{
        private String sub;
        private String name;
        private String email;
    }
}
