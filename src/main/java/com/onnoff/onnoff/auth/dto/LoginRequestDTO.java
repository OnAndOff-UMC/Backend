package com.onnoff.onnoff.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class LoginRequestDTO {

    @Getter
    public static class AppleTokenValidateDTO{
        private String oauthId;
        private Fullname fullName;
        private String email;
        private String identityToken;
        private String authorizationCode;
        private AdditionalInfo additionalInfo;
    }
    @Getter
    public static class KakaoTokenValidateDTO{
        private String identityToken;
        private String accessToken;
        private AdditionalInfo additionalInfo;
    }

    @Getter
    public static class AdditionalInfo{
        private String nickname;
        private String fieldOfWork;
        private String job;
        private String experienceYear;
    }
    @Getter
    public static class Fullname{
        private String givenName;
        private String familyName;
    }
}
