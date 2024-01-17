package com.onnoff.onnoff.domain.user.converter;

import com.onnoff.onnoff.auth.client.dto.KakaoOauth2DTO;
import com.onnoff.onnoff.domain.user.User;

public class UserConverter {
    public static User toUser(KakaoOauth2DTO.UserInfoResponseDTO response){
        KakaoOauth2DTO.KakaoAccountDTO kakaoAccount = response.getKakaoAccount();
        return User.builder()
                .oauthId(response.getId())
                .email(kakaoAccount.getEmail())
                .name(kakaoAccount.getName())
                .build();
    }
}
