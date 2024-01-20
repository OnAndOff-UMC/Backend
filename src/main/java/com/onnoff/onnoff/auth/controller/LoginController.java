package com.onnoff.onnoff.auth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.auth.client.dto.KakaoOauth2DTO;
import com.onnoff.onnoff.auth.dto.LoginResponseDTO;
import com.onnoff.onnoff.auth.service.LoginService;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.converter.UserConverter;
import com.onnoff.onnoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;


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
        log.info("code = {}", code);
        loginService.getAccessToken(code);
        return ResponseEntity.ok("토큰 get");
    }
    /*
    1. 토큰 유효성 검증
    2. 사용자 정보 얻어오기
    3. DB조회 및 추가
     */
    // 나중에 accessToken RequestBody로 받도록 수정
    @GetMapping("/oauth2/kakao/token/validate")
    public ApiResponse<Object> validateToken(@RequestParam String accessToken) throws JsonProcessingException {
        accessToken = "Bearer " + accessToken;
        // 토큰 검증
        loginService.validate(accessToken);
        // ok -> 유저 정보 가져오기, error -> 에러 응답 코드 반환
        KakaoOauth2DTO.UserInfoResponseDTO userInfoResponseDTO = loginService.getUserInfo(accessToken);
        // 유저 정보에 DB 조회하고 정보 있으면 응답만, 없으면 저장까지 이 두 경우의 성공 응답코드 다르게
        Long oauthId = userInfoResponseDTO.getId();
        if( userService.isExistByOauthId(oauthId)){
            return ApiResponse.onSuccess(new Object()); //응답 DTO 만들어야함
        }
        else{
            User user = UserConverter.toUser(userInfoResponseDTO);
            userService.create(user);
        }
        return null;
    }
}
