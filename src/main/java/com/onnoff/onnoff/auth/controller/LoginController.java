package com.onnoff.onnoff.auth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.auth.dto.LoginRequestDTO;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import com.onnoff.onnoff.auth.feignClient.dto.kakao.KakaoOauth2DTO;
import com.onnoff.onnoff.auth.jwt.dto.JwtToken;
import com.onnoff.onnoff.auth.jwt.service.JwtUtil;
import com.onnoff.onnoff.auth.service.AppleLoginService;
import com.onnoff.onnoff.auth.service.KakaoLoginService;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.converter.UserConverter;
import com.onnoff.onnoff.domain.user.dto.UserResponseDTO;
import com.onnoff.onnoff.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final JwtUtil jwtUtil;



    /*
    테스트용 API, CORS 때문에 직접 호출하지 않고 redirect
     */
    @GetMapping("/oauth2/authorize/kakao")
    public String login(){
        String redirectUri = UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "32c0787d1b1e9fcabcc24af247903ba8")
                .queryParam("redirect_uri", "http://localhost:8080/oauth2/login/kakao")
                .toUriString();
        return "redirect:" + redirectUri;
    }
    /*
    테스트용 API
     */
    @GetMapping("/oauth2/login/kakao")
    public ResponseEntity<String> getAccessToken(@RequestParam(name = "code") String code){
        TokenResponse tokenResponse = kakaoLoginService.getAccessTokenByCode(code);
        return ResponseEntity.ok("http://localhost:8080/oauth2/kakao/token/validate?accessToken="+ tokenResponse.getAccessToken());
    }
    /*
    1. ID 토큰 유효성 검증
    2. 사용자 정보 얻어오기
    3. DB 조회 및 추가
    4. 응답 헤더에 Jwt 토큰 추가
     */

    @Operation(summary = "토큰 검증 API",description = "토큰을 검증 하고 이에 대한 결과를 응답합니다. 추가 정보 입력 여부도 같이 응답 합니다.")
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
            user = UserConverter.toUser(userInfo);
            user = userService.create(user);
        }
        // 응답헤더에 토큰 추가
        JwtToken token = jwtUtil.generateToken(String.valueOf(user.getId()));
        response.addHeader("Access-Token", token.getAccessToken());
        response.addHeader("Refresh-Token", token.getRefreshToken());
        return ApiResponse.onSuccess(UserConverter.toLoginDTO(user));
    }

    @ResponseBody
    @PostMapping("/oauth2/apple/token/validate")
    public ApiResponse<?> validateAppleToken(HttpServletResponse response, @RequestBody LoginRequestDTO.AppleTokenValidateDTO requestDTO)  {
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
            user = UserConverter.toUser(requestDTO);
            user.setAppleRefreshToken(tokenResponse.getRefreshToken());
            user = userService.create(user);
        }
        // 응답헤더에 토큰 추가
        JwtToken token = jwtUtil.generateToken(String.valueOf(user.getId()));
        response.addHeader("Access-Token", token.getAccessToken());
        response.addHeader("Refresh-Token", token.getRefreshToken());
        return ApiResponse.onSuccess(UserConverter.toLoginDTO(user));
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
