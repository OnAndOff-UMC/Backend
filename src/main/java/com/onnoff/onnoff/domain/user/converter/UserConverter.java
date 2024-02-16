package com.onnoff.onnoff.domain.user.converter;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.auth.dto.LoginRequestDTO;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.KakaoOauth2DTO;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.dto.UserResponseDTO;
import com.onnoff.onnoff.domain.user.enums.ExperienceYear;
import com.onnoff.onnoff.domain.user.enums.FieldOfWork;
import com.onnoff.onnoff.domain.user.enums.SocialType;

import java.util.EnumSet;

public class UserConverter {
    public static User toUser(KakaoOauth2DTO.UserInfoResponseDTO response, LoginRequestDTO.AdditionalInfo additionalInfo){
        FieldOfWork fieldOfWork = FieldOfWork.fromValue(additionalInfo.getFieldOfWork());
        ExperienceYear experienceYear = ExperienceYear.fromValue(additionalInfo.getExperienceYear());
        return User.builder()
                .oauthId(response.getSub())
                .email(response.getEmail())
                .name(response.getNickname())
                .nickname(additionalInfo.getNickname())
                .socialType(SocialType.KAKAO)
                .fieldOfWork(fieldOfWork)
                .job(additionalInfo.getJob())
                .experienceYear(experienceYear)
                .build();
    }
    public static User toUser(LoginRequestDTO.AppleTokenValidateDTO request, LoginRequestDTO.AdditionalInfo additionalInfo){
        FieldOfWork fieldOfWork = FieldOfWork.fromValue(additionalInfo.getFieldOfWork());
        ExperienceYear experienceYear = ExperienceYear.fromValue(additionalInfo.getExperienceYear());
        // String fullName = request.getFullName().getFamilyName() + request.getFullName().getGivenName();
        return User.builder()
                .oauthId(request.getOauthId())
                //.email(request.getEmail())
                //.name(fullName)
                .nickname(additionalInfo.getNickname())
                .socialType(SocialType.APPLE)
                .fieldOfWork(fieldOfWork)
                .job(additionalInfo.getJob())
                .experienceYear(experienceYear)
                .build();
    }
    public static UserResponseDTO.LoginDTO toLoginDTO(String accessToken, String refreshToken){
        return UserResponseDTO.LoginDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }
    public static UserResponseDTO.UserDetailDTO toUserDetailDTO(User user){
        return UserResponseDTO.UserDetailDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .socialType(user.getSocialType())
                .nickname(user.getNickname())
                .fieldOfWork(user.getFieldOfWork())
                .job(user.getJob())
                .experienceYear(user.getExperienceYear())
                .infoSet(user.isInfoSet())
                .status(user.getStatus())
                .inactiveDate(user.getInactiveDate())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static UserResponseDTO.UserInformationResponseDTO toUserInformationResponseDTO(User user){
        return UserResponseDTO.UserInformationResponseDTO.builder()
                .nickname(user.getNickname())
                .experienceYear(user.getExperienceYear())
                .fieldOfWork(user.getFieldOfWork())
                .build();
    }

}
