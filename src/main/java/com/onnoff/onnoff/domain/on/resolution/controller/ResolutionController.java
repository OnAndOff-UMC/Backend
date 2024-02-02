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
    public ApiResponse<ResolutionResponse.ResolutionViewDTO> getResolutions(@RequestParam(name = "date") LocalDate date){
        List<Resolution> resolutionList = resolutionService.getAll(date);
        return ApiResponse.onSuccess(ResolutionConverter.toResolutionViewDTO(date, resolutionList));
    }

    @PostMapping("/")
    @Operation(summary = "오늘의 다짐 추가 API")
    public ApiResponse<ResolutionResponse.AddResultDTO> addResolution(@RequestBody @Valid ResolutionRequest.AddResolutionDTO request){
        Resolution resolution = resolutionService.addResolution(request);
        return ApiResponse.onSuccess(ResolutionConverter.toAddResolutionResultDTO(resolution));
    }

    @PutMapping("/")
    @Operation(summary = "오늘의 다짐 수정 API")
    public ApiResponse<ResolutionResponse.ResolutionViewDTO> modifyResolution(@RequestBody @Valid ResolutionRequest.ModifyResolutionDTO request){
        List<Resolution> modifiedResolutionList = resolutionService.modifyResolution(request);
        return ApiResponse.onSuccess(ResolutionConverter.toResolutionViewDTO(request.getDate(), modifiedResolutionList));
    }

    @DeleteMapping("/{resolutionId}")
    @Operation(summary = "오늘의 다짐 삭제 API")
    public ApiResponse<Long> deleteResolution(@RequestParam(name = "date") LocalDate date,
                                              @PathVariable(name = "resolutionId") Long resolutionId){
        resolutionService.deleteResolution(date, resolutionId);
        return ApiResponse.onSuccess(null);
    }
}
