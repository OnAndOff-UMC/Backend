package com.onnoff.onnoff.domain.off.memoir.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.off.memoir.converter.MemoirConverter;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirResponseDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import com.onnoff.onnoff.domain.off.memoir.service.MemoirService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoirController {

    private final MemoirService memoirService;

    @GetMapping("/memoir-questions")
    @Operation(summary = "회고 질문 조회 API",description = "회고 질문 목록을 조회하는 API입니다.")
    public ApiResponse<List<MemoirResponseDTO.QuestionResultDTO>> getMemoirQuestion(){
        List<MemoirQuestion> memoirQuestionList = memoirService.getMemoirQuestion();
        return ApiResponse.onSuccess(MemoirConverter.toQuestionResultDTOList(memoirQuestionList));
    }

    @PostMapping("/memoirs")
    @Operation(summary = "회고 작성 API",description = "새로운 회고를 작성하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.ResultDTO> writeMemoir(@RequestBody @Valid MemoirRequestDTO.WriteDTO request){
        Memoir memoir = memoirService.writeMemoir(request);
        return ApiResponse.onSuccess(MemoirConverter.toResultDTO(memoir));
    }

    @GetMapping("/memoirs")
    @Operation(summary = "회고 조회 API",description = "특정한 날짜의 회고를 조회하는 API입니다. Query String으로 날짜를 입력해 주세요.")
    public ApiResponse<MemoirResponseDTO.ResultDTO> getMemoir(@RequestParam(name = "date") LocalDate date){
        Memoir memoir = memoirService.getMemoir(date);

        if (memoir == null) {
            return ApiResponse.onSuccess(null);
        }
        return ApiResponse.onSuccess(MemoirConverter.toResultDTO(memoir));
    }

    @PatchMapping("/memoirs")
    @Operation(summary = "회고 수정 API",description = "기존의 회고를 수정하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.ResultDTO> updateMemoir(@RequestBody @Valid MemoirRequestDTO.UpdateDTO request){
        Memoir memoir = memoirService.updateMemoir(request);
        return ApiResponse.onSuccess(MemoirConverter.toResultDTO(memoir));
    }

    @DeleteMapping("/memoirs/{memoirId}")
    @Operation(summary = "회고 삭제 API",description = "기존의 회고를 삭제하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.ResultDTO> deleteMemoir(@PathVariable(name = "memoirId") Long memoirId){
        Memoir memoir = memoirService.deleteMemoir(memoirId);
        return ApiResponse.onSuccess(MemoirConverter.toResultDTO(memoir));
    }
}
