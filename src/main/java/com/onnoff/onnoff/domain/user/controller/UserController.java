package com.onnoff.onnoff.domain.user.controller;


import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.push.dto.PushNotificationSettingRequestDTO;
import com.onnoff.onnoff.domain.push.dto.PushNotificationSettingResponseDTO;
import com.onnoff.onnoff.domain.push.service.PushNotificationSettingService;
import com.onnoff.onnoff.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    public final UserService userService;
    public final PushNotificationSettingService pushNotificationSettingService;
    @PostMapping("/pushNotification")
    @Operation(summary = "유저 푸시알람 설정 및 수정", description = "유저의 푸시알람 설정을 수정하는 API입니다.")
    public ApiResponse<PushNotificationSettingResponseDTO> setPushNotificationSetting(@RequestBody @Valid PushNotificationSettingRequestDTO pushNotificationSettingRequestDTO){
        return ApiResponse.onSuccess(pushNotificationSettingService.setPushNotification(pushNotificationSettingRequestDTO));
    }

}
