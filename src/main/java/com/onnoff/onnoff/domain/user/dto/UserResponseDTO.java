package com.onnoff.onnoff.domain.user.dto;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.user.enums.ExperienceYear;
import com.onnoff.onnoff.domain.user.enums.FieldOfWork;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import com.onnoff.onnoff.domain.user.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO{
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDetailDTO{
        private Long id;
        private boolean infoSet; //추가 정보 기입 여부
        private String name;
        private String nickname;
        private String email;
        private FieldOfWork fieldOfWork;
        private String job;
        private ExperienceYear experienceYear;
        private Status status;
        private LocalDateTime inactiveDate;
        private boolean receivePushNotification;
        private SocialType socialType;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInformationResponseDTO {
        private String nickname;
        private FieldOfWork fieldOfWork;
        private ExperienceYear experienceYear;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserModificationResponseDTO {
        private String nickname;
        private String fieldOfWork;
        private String experienceYear;
        private String job;
    }
}
