package com.onnoff.onnoff.domain.weekday.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.weekday.dto.WeekdayResponseDTO;
import com.onnoff.onnoff.domain.weekday.service.WeekdayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/weekdays/prev")
    @Operation(summary = "이전 주 요일 조회 API", description = "입력된 날짜를 기준으로 이전 일주일을 조회하는 API입니다. Query String으로 날짜를 입력해 주세요.")
    public ApiResponse<WeekdayResponseDTO.WeekdayResultDTO> getPrevWeekday(@RequestParam(name = "date") LocalDate date) {
        return ApiResponse.onSuccess(weekdayService.getWeekday(date.minusDays(7)));
    }

    @GetMapping("/weekdays/next")
    @Operation(summary = "다음 주 요일 조회 API", description = "입력된 날짜를 기준으로 다음 일주일을 조회하는 API입니다. Query String으로 날짜를 입력해 주세요.")
    public ApiResponse<WeekdayResponseDTO.WeekdayResultDTO> getNextWeekday(@RequestParam(name = "date") LocalDate date) {
        return ApiResponse.onSuccess(weekdayService.getWeekday(date.plusDays(7)));
    }
}
