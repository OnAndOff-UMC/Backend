package com.onnoff.onnoff.auth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.auth.dto.LoginRequestDTO;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.KakaoOauth2DTO;
import com.onnoff.onnoff.auth.jwt.dto.JwtToken;
import com.onnoff.onnoff.auth.jwt.service.JwtTokenProvider;
import com.onnoff.onnoff.auth.jwt.service.JwtUtil;
import com.onnoff.onnoff.auth.service.AppleLoginService;
import com.onnoff.onnoff.auth.service.KakaoLoginService;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.converter.UserConverter;
import com.onnoff.onnoff.domain.user.dto.UserResponseDTO;
import com.onnoff.onnoff.domain.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final KakaoLoginService kakaoLoginService;
    private final AppleLoginService appleLoginService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${kakao.client-id}")
    private String kakaoClientId;



    /*
    테스트용 API, CORS 때문에 직접 호출하지 않고 redirect
     */
    @GetMapping("/oauth2/authorize/kakao")
    public String login(){
        String toRedirectUri = UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", redirectUri)
                .toUriString();
        return "redirect:" + toRedirectUri;
    }
    /*
    테스트용 API
     */
    @GetMapping("/oauth2/login/kakao")
    public ResponseEntity<String> getAccessToken(@RequestParam(name = "code") String code){
        TokenResponse tokenResponse = kakaoLoginService.getAccessTokenByCode(code);
        return ResponseEntity.ok("accessToken="+ tokenResponse.getAccessToken() +
                "\n\nidToken=" + tokenResponse.getIdToken());
    }
    /*
    1. ID 토큰 유효성 검증
    2. 사용자 정보 얻어오기
    3. DB 조회 및 추가
    4. 응답 헤더에 Jwt 토큰 추가
     */

    @Operation(summary = "소셜 토큰 검증 API",description = "토큰을 검증 하고 이에 대한 결과를 응답합니다. 추가 정보 입력 여부도 같이 응답 합니다.")
    @ResponseBody
    @PostMapping("/oauth2/kakao/token/validate")
    public ApiResponse<UserResponseDTO.LoginDTO> validateKakoToken(HttpServletResponse response, @RequestBody LoginRequestDTO.KakaoTokenValidateDTO requestDTO)  {
        // identity 토큰 검증
        kakaoLoginService.validate(requestDTO.getIdentityToken());
        // ok -> 유저 정보 가져오기
        KakaoOauth2DTO.UserInfoResponseDTO userInfo;
        try {
            userInfo = kakaoLoginService.getUserInfo(requestDTO.getAccessToken());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // 유저 정보에 DB 조회하고 정보 있으면 응답만, 없으면 저장까지, 추가정보 입력 여부에 따라서 응답 다르게
        String oauthId = userInfo.getSub();
        User user;
        if( userService.isExistByOauthId(oauthId)){
            user = userService.getUserByOauthId(oauthId);
        }
        else{
            user = UserConverter.toUser(userInfo, requestDTO.getAdditionalInfo());
            user = userService.create(user);
        }
        // 응답본문에 토큰 추가
        JwtToken token = jwtUtil.generateToken(String.valueOf(user.getId()));
        return ApiResponse.onSuccess(UserConverter.toLoginDTO(token.getAccessToken(), token.getRefreshToken()));
    }

    @ResponseBody
    @PostMapping("/oauth2/apple/token/validate")
    public ApiResponse<UserResponseDTO.LoginDTO> validateAppleToken(HttpServletResponse response, @RequestBody LoginRequestDTO.AppleTokenValidateDTO requestDTO)  {
        // 검증하기
        appleLoginService.validate(requestDTO.getIdentityToken());
        // 검증 성공 시 리프레시 토큰 발급받아 저장(기한 무제한, 회원탈퇴 시 필요)
        TokenResponse tokenResponse = appleLoginService.getAccessTokenByCode(requestDTO.getAuthorizationCode());
        // 유저 정보 조회 및 저장
        String oauthId = requestDTO.getOauthId();
        User user;
        if( userService.isExistByOauthId(oauthId)){
            user = userService.getUserByOauthId(oauthId);
        }
        else{
            user = UserConverter.toUser(requestDTO, requestDTO.getAdditionalInfo());
            user.setAppleRefreshToken(tokenResponse.getRefreshToken());
            user = userService.create(user);
        }
        // 응답헤더에 토큰 추가
        JwtToken token = jwtUtil.generateToken(String.valueOf(user.getId()));
        return ApiResponse.onSuccess(UserConverter.toLoginDTO(token.getAccessToken(), token.getRefreshToken()));
    }

    @Operation(summary = "서버 토큰 검증 API",description = "토큰의 유효성을 검증하고 액세스 토큰이 만료되었으면" +
            "재발급, 리프레시 토큰까지 만료되었으면 4001 오류 응답")
    @ResponseBody
    @PostMapping("/token/validate")
    public ApiResponse<UserResponseDTO.LoginDTO> validateServerToken(@RequestBody JwtToken tokenDTO){
        String accessToken = tokenDTO.getAccessToken();
        String refreshToken = tokenDTO.getRefreshToken();
        if( jwtTokenProvider.verifyToken(accessToken) ){
            // accessToken 유효
            String userId = jwtUtil.getUserId(accessToken);
            User user = userService.getUser(Long.valueOf(userId));
            UserResponseDTO.LoginDTO loginDTO = UserConverter.toLoginDTO(accessToken, refreshToken);
            return ApiResponse.onSuccess(loginDTO);
        }
        if (jwtTokenProvider.verifyToken(refreshToken)) {
            //refreshToken 유효
            String userId = jwtUtil.getUserId(refreshToken);
            User user = userService.getUser(Long.valueOf(userId));

            String newRefreshToken = jwtUtil.generateRefreshToken(jwtUtil.getUserId(refreshToken));
            String newAccessToken = jwtUtil.generateAccessToken(jwtUtil.getUserId(refreshToken));
            return ApiResponse.onSuccess(UserConverter.toLoginDTO(newAccessToken, newRefreshToken));
        }
        // 둘 다 유효하지 않은 경우
        throw new GeneralException(ErrorStatus.INVALID_TOKEN_ERROR);
    }
    /*
   테스트용 API
    */
    @Operation(summary = "UserContext Test  API",description = "jwt 토큰 검증 성공 시 유저 객체 저장한 거 조회 가능한지 테스트")
    @GetMapping("/test")
    public ResponseEntity<String> testAfterGetToken(){
        log.info("authenticatedUser = {}", UserContext.getUser()); // 테스트 성공
        return ResponseEntity.ok("");
    }
}
