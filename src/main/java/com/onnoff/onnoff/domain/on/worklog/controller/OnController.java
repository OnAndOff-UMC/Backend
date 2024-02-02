package com.onnoff.onnoff.domain.on.worklog.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.on.worklog.dto.OnResponse;
import com.onnoff.onnoff.domain.on.worklog.service.OnService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/on")
public class OnController {
    private final OnService onService;

    @GetMapping("/")
    @Operation(summary = "ON 화면 조회")
    public ApiResponse<OnResponse.OnViewDTO> getOn(@RequestParam(name = "date", required = false) LocalDate date){
        return ApiResponse.onSuccess(onService.getOn(date));
    }
}
