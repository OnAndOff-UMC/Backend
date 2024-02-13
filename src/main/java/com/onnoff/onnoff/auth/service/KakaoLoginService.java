package com.onnoff.onnoff.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onnoff.onnoff.auth.feignClient.client.KakaoApiClient;
import com.onnoff.onnoff.auth.feignClient.client.KakaoOauth2Client;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.KakaoOauth2DTO;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.UnlinkRequest;
import com.onnoff.onnoff.auth.service.tokenValidator.SocialTokenValidator;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;


@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginService implements LoginService{
    private final KakaoOauth2Client kakaoOauth2Client;
    private final KakaoApiClient kakaoApiClient;
    private final SocialTokenValidator validator;
    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${kakao.admin-key}")
    private String adminKey;
    /*
        테스트 용으로 만든거, 실제로는 프론트에서 처리해서 액세스 토큰만 가져다 줌
    */
    @Override
    public TokenResponse getAccessTokenByCode(String code){
        return kakaoOauth2Client.getAccessToken("authorization_code",
                clientId,
                redirectUri,
                code);
    }
    // id 토큰 유효성 검증
    @Override
    public void validate(String idToken){
        String cleanedAccessToken = cleanToken(idToken);
        validator.validate(cleanedAccessToken, SocialType.KAKAO);
    }

    public void revokeTokens(String oauthId) {
        MultiValueMap<String, String> urlEncoded = UnlinkRequest.builder()
                .targetIdType("user_id")
                .targetId(oauthId)
                .build()
                .toUrlEncoded();
        log.info("adminkey = {}", adminKey);
        ResponseEntity responseEntity = kakaoApiClient.unlink("KakaoAK " + adminKey, urlEncoded);
        log.info("삭제된 회원 정보 = {}", responseEntity.getBody());
    }

    /*
       토큰으로 유저정보를 가져오는 메서드
    */
    public KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(String accessToken) throws JsonProcessingException {
        String cleanedAccessToken = cleanToken(accessToken);
        accessToken = "bearer " + cleanedAccessToken;
        KakaoOauth2DTO.UserInfoResponseDTO userInfo = kakaoApiClient.getUserInfo(accessToken);
        return userInfo;
    }
}
