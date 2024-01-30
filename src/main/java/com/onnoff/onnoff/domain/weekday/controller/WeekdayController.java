package com.onnoff.onnoff.domain.weekday.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.weekday.dto.WeekdayResponseDTO;
import com.onnoff.onnoff.domain.weekday.service.WeekdayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class WeekdayController {

    private final WeekdayService weekdayService;

    @GetMapping("/weekdays/init")
    @Operation(summary = "초기 요일 조회 API", description = "오늘 날짜를 기준으로 일주일을 조회하는 API입니다.")
    public ApiResponse<WeekdayResponseDTO.WeekdayResultDTO> getInitWeekday() {
        return ApiResponse.onSuccess(weekdayService.getWeekday(LocalDate.now()));
    }
}
