package com.onnoff.onnoff.domain.stats.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.stats.dto.StatsResponseDTO;
import com.onnoff.onnoff.domain.stats.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/week")
    @Operation(summary = "요일별 달성률")
    public ApiResponse<StatsResponseDTO.WeekDTO> getWeekStats(@RequestParam(name = "userId") Long userId){
        return ApiResponse.onSuccess(statsService.getWeekStats(userId));
    }
}