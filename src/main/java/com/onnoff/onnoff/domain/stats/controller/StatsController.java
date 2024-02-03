package com.onnoff.onnoff.domain.stats.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.stats.dto.StatsResponseDTO;
import com.onnoff.onnoff.domain.stats.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/week")
    @Operation(summary = "요일별 달성률")
    public ApiResponse<StatsResponseDTO.WeekDTO> getWeekStats(){
        return ApiResponse.onSuccess(statsService.getWeekStats());
    }

    @GetMapping("/month")
    @Operation(summary = "월별 달성률")
    public ApiResponse<StatsResponseDTO.MonthDTO> getMonthStats(){
        return ApiResponse.onSuccess(statsService.getMonthStats(null));
    }

    @GetMapping("/month/prev")
    @Operation(summary = "이전 달 달성률")
    public ApiResponse<StatsResponseDTO.MonthDTO> moveToPrevMonth(@RequestParam(name = "date") LocalDate date){
        return ApiResponse.onSuccess(statsService.getMonthStats(date.minusMonths(1)));
    }

    @GetMapping("/month/next")
    @Operation(summary = "다음 달 달성률")
    public ApiResponse<StatsResponseDTO.MonthDTO> moveToNextMonth(@RequestParam(name = "date") LocalDate date){
        return ApiResponse.onSuccess(statsService.getMonthStats(date.plusMonths(1)));
    }
}