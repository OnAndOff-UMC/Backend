package com.onnoff.onnoff.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
        @Valid
        private AdditionalInfo additionalInfo;
    }

    @Getter
    public static class AdditionalInfo{
        @NotNull
        @NotBlank
        @Size(max = 10)
        private String nickname;
        private String fieldOfWork;
        @Size(max = 30)
        private String job;
        private String experienceYear;
    }
    @Getter
    public static class Fullname{
        @NotNull
        private String givenName;
        @NotNull
        private String familyName;
    }
}
