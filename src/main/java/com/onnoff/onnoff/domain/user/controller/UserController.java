package com.onnoff.onnoff.domain.user.controller;


import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.on.resolution.converter.ResolutionConverter;
import com.onnoff.onnoff.domain.user.converter.UserConverter;
import com.onnoff.onnoff.domain.user.dto.EnumInquiryResponseDTO;
import com.onnoff.onnoff.domain.user.dto.UserRequestDTO;
import com.onnoff.onnoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/information")
    public ApiResponse getUserInformation(){
        return ApiResponse.onSuccess(UserConverter.toUserInformationResponseDTO(UserContext.getUser()));
    }

    @PutMapping("/withdraw")
    public ApiResponse withdrawUser(){
        return ApiResponse.onSuccess(UserConverter.toUserDetailDTO(userService.withdrawUser()));
    }

    @PutMapping("/")
    public ApiResponse modifyUser(@RequestBody UserRequestDTO.ModifyUserDTO modifyUserDTO) {
        return ApiResponse.onSuccess(UserConverter.toUserDetailDTO(userService.modifyUser(modifyUserDTO)));
    }
}
