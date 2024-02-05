package com.onnoff.onnoff.domain.user.controller;


import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.on.resolution.converter.ResolutionConverter;
import com.onnoff.onnoff.domain.user.converter.UserConverter;
import com.onnoff.onnoff.domain.user.dto.EnumInquiryResponseDTO;
import com.onnoff.onnoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ApiResponse getUserInfo(){
        return ApiResponse.onSuccess(UserConverter.toUserInfoResponseDTO(UserContext.getUser()));
    }

}
