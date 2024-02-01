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
    @Operation(summary = "회고 질문 조회 API", description = "회고 질문 목록을 조회하는 API입니다.")
    public ApiResponse<List<MemoirResponseDTO.MemoirQuestionResultDTO>> getMemoirQuestion() {
        List<MemoirQuestion> memoirQuestionList = memoirService.getMemoirQuestion();
        return ApiResponse.onSuccess(MemoirConverter.toMemoirQuestionResultDTOList(memoirQuestionList));
    }

    @PostMapping("/memoirs")
    @Operation(summary = "회고 작성 API", description = "새로운 회고를 작성하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.MemoirResultDTO> writeMemoir(@RequestBody @Valid MemoirRequestDTO.MemoirWriteDTO request) {
        Memoir memoir = memoirService.writeMemoir(request);
        return ApiResponse.onSuccess(MemoirConverter.toMemoirResultDTO(memoir));
    }

    @GetMapping("/memoirs/previews")
    @Operation(summary = "회고 미리보기 조회 API", description = "특정 날짜의 회고 미리보기를 조회하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.MemoirPreviewResultDTO> getMemoirPreview(@RequestParam(name = "date") LocalDate date) {
        Memoir memoir = memoirService.getMemoirPreview(date);
        return ApiResponse.onSuccess(MemoirConverter.toMemoirPreviewResultDTO(memoir));
    }

    @GetMapping("/memoirs/{memoirId}")
    @Operation(summary = "회고 조회 API", description = "특정 회고를 조회하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.MemoirResultDTO> getMemoir(@PathVariable(name = "memoirId") Long memoirId) {
        Memoir memoir = memoirService.getMemoir(memoirId);
        return ApiResponse.onSuccess(MemoirConverter.toMemoirResultDTO(memoir));
    }

    @GetMapping("/memoirs/bookmarks")
    @Operation(summary = "북마크 회고 조회 API", description = "북마크 상태의 회고를 조회하는 API입니다.")
    public ApiResponse<List<MemoirResponseDTO.BookmarkedMemoirResultDTO>> getBookmarkedMemoir() {
        List<Memoir> memoirList = memoirService.getBookmarkedMemoir();
        return ApiResponse.onSuccess(MemoirConverter.toBookmarkedMemoirResultDTOList(memoirList));
    }

    @PatchMapping("/memoirs/{memoirId}")
    @Operation(summary = "회고 내용 수정 API", description = "기존의 회고 내용을 수정하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.MemoirResultDTO> modifyMemoir(@PathVariable(name = "memoirId") Long memoirId,
                                                                       @RequestBody @Valid MemoirRequestDTO.MemoirUpdateDTO request) {
        Memoir memoir = memoirService.modifyMemoir(memoirId, request);
        return ApiResponse.onSuccess(MemoirConverter.toMemoirResultDTO(memoir));
    }

    @PatchMapping("/memoirs/{memoirId}/bookmark")
    @Operation(summary = "회고 북마크 및 해제 API", description = "회고를 북마크하거나 북마크 해제하는 API입니다.")
    public ApiResponse<MemoirResponseDTO.MemoirResultDTO> bookmarkMemoir(@PathVariable(name = "memoirId") Long memoirId) {
        Memoir memoir = memoirService.bookmarkMemoir(memoirId);
        return ApiResponse.onSuccess(MemoirConverter.toMemoirResultDTO(memoir));
    }

    @DeleteMapping("/memoirs/{memoirId}")
    @Operation(summary = "회고 삭제 API", description = "기존의 회고를 삭제하는 API입니다.")
    public ApiResponse<Long> deleteMemoir(@PathVariable(name = "memoirId") Long memoirId) {
        return ApiResponse.onSuccess(memoirService.deleteMemoir(memoirId));
    }
}
