package com.onnoff.onnoff.domain.user.dto;

import com.onnoff.onnoff.domain.user.enums.ExperienceYear;
import com.onnoff.onnoff.domain.user.enums.FieldOfWork;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import com.onnoff.onnoff.domain.user.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyUserDTO{
        private String nickname;
        private String fieldOfWork;
        private String job;
        private String experienceYear;
    }

    @Getter
    public static class getNicknameDTO{
        @NotBlank
        @Size(max = 10)
        String nickname;
    }
}
