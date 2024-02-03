package com.onnoff.onnoff.domain.user.converter;

import com.onnoff.onnoff.auth.dto.LoginRequestDTO;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.KakaoOauth2DTO;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.dto.UserResponseDTO;
import com.onnoff.onnoff.domain.user.enums.SocialType;

public class UserConverter {
    public static User toUser(KakaoOauth2DTO.UserInfoResponseDTO response){
        return User.builder()
                .oauthId(response.getSub())
                .email(response.getEmail())
                .name(response.getNickname())
                .socialType(SocialType.KAKAO)
                .build();
    }
    public static User toUser(LoginRequestDTO.AppleTokenValidateDTO request){
        return User.builder()
                .oauthId(request.getOauthId())
                .email(request.getEmail())
                .name(request.getFullName())
                .socialType(SocialType.APPLE)
                .build();
    }
    public static UserResponseDTO.LoginDTO toLoginDTO(User user, String accessToken, String refreshToken){
        return UserResponseDTO.LoginDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .infoSet(user.isInfoSet())
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
