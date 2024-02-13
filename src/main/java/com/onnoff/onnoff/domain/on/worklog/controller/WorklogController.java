package com.onnoff.onnoff.domain.on.worklog.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.on.worklog.converter.WorklogConverter;
import com.onnoff.onnoff.domain.on.worklog.dto.WorklogRequest;
import com.onnoff.onnoff.domain.on.worklog.dto.WorklogResponse;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;
import com.onnoff.onnoff.domain.on.worklog.service.WorklogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/on/worklog")
public class WorklogController {
    private final WorklogService worklogService;


    @GetMapping("/")
    @Operation(summary = "업무일지 조회 API")
    public ApiResponse<List<WorklogResponse.WorklogDTO>> getWorklog(@RequestParam(name = "date") LocalDate date){
        List<Worklog> worklogList = worklogService.getWorklog(date);
        return ApiResponse.onSuccess(WorklogConverter.toWorklogViewDTO(worklogList));
    }


    @PostMapping("/")
    @Operation(summary = "업무일지 추가 API")
    public ApiResponse<WorklogResponse.AddResultDTO> addWorklog(@RequestBody @Valid WorklogRequest.AddWorklogDTO request){
        Worklog worklog = worklogService.addWorklog(request);
        return ApiResponse.onSuccess(WorklogConverter.toAddWorklogResultDTO(worklog));
    }


    @PostMapping("/{worklogId}")
    @Operation(summary = "업무일지 수정 API")
    public ApiResponse<WorklogResponse.ModifyResultDTO> modifyWorklog(@PathVariable(name = "worklogId") Long worklogId,
                                                                      @RequestBody @Valid WorklogRequest.ModifyWorklogDTO request){
        Worklog modifiedWorklog = worklogService.modifyContent(worklogId, request);
        return ApiResponse.onSuccess(WorklogConverter.toModifyWorklogResultDTO(modifiedWorklog));
    }


    @PatchMapping("/{worklogId}")
    @Operation(summary = "업무일지 내일로 미루기 API")
    public ApiResponse<WorklogResponse.ModifyResultDTO> modifyWorklogDate(@PathVariable(name = "worklogId") Long worklogId){
        Worklog modifiedWorklog = worklogService.modifyDate(worklogId);
        return ApiResponse.onSuccess(WorklogConverter.toModifyWorklogResultDTO(modifiedWorklog));
    }


    @PutMapping("/{worklogId}")
    @Operation(summary = "업무일지 체크 API")
    public ApiResponse<WorklogResponse.ModifyResultDTO> modifyWorklogIsChecked(@PathVariable(name = "worklogId") Long worklogId){
        Worklog modifiedWorklog = worklogService.modifyIsChecked(worklogId);
        return ApiResponse.onSuccess(WorklogConverter.toModifyWorklogResultDTO(modifiedWorklog));
    }


    @DeleteMapping("/{worklogId}")
    @Operation(summary = "업무일지 삭제 API")
    public ApiResponse<Long> deleteWorklog(@PathVariable(name = "worklogId") Long worklogId){
        worklogService.deleteWorklog(worklogId);
        return ApiResponse.onSuccess(null);
    }
}
