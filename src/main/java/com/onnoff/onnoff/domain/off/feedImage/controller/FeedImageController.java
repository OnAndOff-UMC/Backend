package com.onnoff.onnoff.domain.off.feedImage.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import com.onnoff.onnoff.domain.off.feedImage.service.FeedImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedImageController {

    private final FeedImageService feedImageService;

    @PostMapping(value = "/feed-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "워라벨 피드 사진 업로드 API",description = "워라벨 피드에 사진을 업로드하는 API입니다.")
    public ApiResponse<FeedImageResponseDTO.FeedImageResultDTO> uploadFeedImage(@RequestPart(name = "image") MultipartFile multipartFile) {
        return ApiResponse.onSuccess(feedImageService.uploadFeedImage(multipartFile));
    }

    @GetMapping("/feed-images")
    @Operation(summary = "워라벨 피드 사진 조회 API",description = "워라벨 피드의 사진을 조회하는 API입니다. 위치를 기준으로 오름차순 정렬된 결과가 반환됩니다.")
    public ApiResponse<List<FeedImageResponseDTO.FeedImageResultDTO>> getFeedImage() {
        return ApiResponse.onSuccess(feedImageService.getFeedImage());
    }

    @DeleteMapping("/feed-images/{feedImageId}")
    @Operation(summary = "워라벨 피드 사진 삭제 API",description = "워라벨 피드의 사진을 삭제하는 API입니다.")
    public ApiResponse<Long> deleteFeedImage(@PathVariable(name = "feedImageId") Long feedImageId) {
        return ApiResponse.onSuccess(feedImageService.deleteFeedImage(feedImageId));
    }
}
