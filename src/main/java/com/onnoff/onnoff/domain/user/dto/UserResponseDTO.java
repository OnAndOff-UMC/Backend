package com.onnoff.onnoff.domain.user.dto;

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
        private Long id;
        private boolean infoSet;
        private LocalDateTime createdAt;
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
        private String fieldOfWork;
        private String job;
        private String experienceYear;
        private Status status;
        private LocalDateTime inactiveDate;
        private boolean receivePushNotification;
        private SocialType socialType;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
