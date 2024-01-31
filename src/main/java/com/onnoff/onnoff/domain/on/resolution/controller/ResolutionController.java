package com.onnoff.onnoff.domain.on.resolution.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.on.resolution.converter.ResolutionConverter;
import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionResponse;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.on.resolution.service.ResolutionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/on/resolution")
public class ResolutionController {
    private final ResolutionService resolutionService;

    @GetMapping("/")
    @Operation(summary = "오늘의 다짐 조회 API")
    public ApiResponse<ResolutionResponse.ResolutionViewDTO> getResolutions(@RequestParam(name = "userId") Long userId,
                                                                            @RequestParam(name = "date") LocalDate date){
        List<Resolution> resolutionList = resolutionService.getAll(userId, date);
        return ApiResponse.onSuccess(ResolutionConverter.toResolutionViewDTO(userId, date, resolutionList));
    }

    @PostMapping("/")
    @Operation(summary = "오늘의 다짐 추가 API")
    public ApiResponse<ResolutionResponse.AddResultDTO> addResolution(@RequestParam(name = "userId") Long userId,
                                                                      @RequestBody @Valid ResolutionRequest.AddResolutionDTO request){
        Resolution resolution = resolutionService.addResolution(userId, request);
        return ApiResponse.onSuccess(ResolutionConverter.toAddResolutionResultDTO(resolution));
    }

    @PutMapping("/")
    @Operation(summary = "오늘의 다짐 수정 API")
    public ApiResponse<ResolutionResponse.ResolutionViewDTO> modifyResolution(@RequestParam(name = "userId") Long userId,
                                                                              @RequestBody @Valid ResolutionRequest.ModifyResolutionDTO request){
        resolutionService.modifyResolution(userId, request);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/{resolutionId}")
    @Operation(summary = "오늘의 다짐 삭제 API")
    public ApiResponse<ResolutionResponse.AddResultDTO> deleteResolution(@RequestParam(name = "userId") Long userId,
                                                                         @RequestParam(name = "date") LocalDate date,
                                                                         @PathVariable(name = "resolutionId") Long resolutionId){
        resolutionService.deleteResolution(userId, date, resolutionId);
        return ApiResponse.onSuccess(null);
    }
}
