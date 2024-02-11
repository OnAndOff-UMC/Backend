package com.onnoff.onnoff.domain.user.controller;


import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.user.converter.UserConverter;
import com.onnoff.onnoff.domain.user.dto.UserRequestDTO;
import com.onnoff.onnoff.domain.user.dto.UserResponseDTO;
import com.onnoff.onnoff.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/information")
    @Operation(summary = "마이페이지 유저 정보 조회 API",description = "마이페이지의 유저 정보를 가져오는 API 입니다.")
    public ApiResponse<UserResponseDTO.UserInformationResponseDTO> getUserInformation(){
        return ApiResponse.onSuccess(UserConverter.toUserInformationResponseDTO(UserContext.getUser()));
    }

    @PutMapping("/withdraw")
    @Operation(summary = "회원 탈퇴 API",description = "유저 회원 탈퇴 API입니다(SOFT DELETE)")
    public ApiResponse<UserResponseDTO.UserDetailDTO> withdrawUser(){
        return ApiResponse.onSuccess(UserConverter.toUserDetailDTO(userService.withdrawUser()));
    }

    @PutMapping("/")
    @Operation(summary = "회원 정보 수정 API" , description = "회원 정보 수정 API입니다.")
    public ApiResponse<UserResponseDTO.UserDetailDTO> modifyUser(@RequestBody UserRequestDTO.ModifyUserDTO modifyUserDTO) {
        return ApiResponse.onSuccess(UserConverter.toUserDetailDTO(userService.modifyUser(modifyUserDTO)));
    }

    //테스트용
    @PutMapping("/hard-delete")
    @Operation(summary = "회원 완전 탈퇴 테스트 API",description = "30일 뒤에 자동 완전삭제 수동 테스트")
    public ApiResponse<String> hardDeleteTest(){
        userService.deleteInactiveUsersTest();
        return ApiResponse.onSuccess("삭제완");
    }

    @PostMapping("/nickname")
    @Operation(summary = "닉네임 중복 체크 API")
    public ApiResponse<String> checkNickname(@Valid @RequestBody UserRequestDTO.getNicknameDTO nicknameDTO){
        return ApiResponse.onSuccess(userService.isExistByNickname(nicknameDTO));
    }
}
