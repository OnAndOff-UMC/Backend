package com.onnoff.onnoff.domain.user.converter;

import com.onnoff.onnoff.auth.feignClient.dto.KakaoOauth2DTO;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.dto.UserResponseDTO;

public class UserConverter {
    public static User toUser(KakaoOauth2DTO.UserInfoResponseDTO response){
        KakaoOauth2DTO.KakaoAccountDTO kakaoAccount = response.getKakaoAccount();
        return User.builder()
                .oauthId(response.getId())
                .email(kakaoAccount.getEmail())
                .name(kakaoAccount.getName())
                .build();
    }

    public static UserResponseDTO.LoginDTO toLoginDTO(User user){
        return UserResponseDTO.LoginDTO.builder()
                .id(user.getId())
                .infoSet(user.isInfoSet())
                .createdAt(user.getCreatedAt())
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
                .receivePushNotification(user.isReceivePushNotification())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
