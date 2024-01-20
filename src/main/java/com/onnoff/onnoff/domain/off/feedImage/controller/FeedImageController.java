package com.onnoff.onnoff.domain.off.feedImage.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import com.onnoff.onnoff.domain.off.feedImage.service.FeedImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FeedImageController {

    private final FeedImageService feedImageService;

    @PostMapping("/feed-images")
    @Operation(summary = "워라벨 피드 사진 업로드 API",description = "워라벨 피드에 사진을 업로드하는 API입니다. Query String으로 사용자 아이디와 사진 위치를 입력해 주세요.")
    public ApiResponse<FeedImageResponseDTO.ResultDTO> uploadFeedImage(@RequestParam(name = "userId") Long userId,
                                                                       @RequestParam(name = "location") Integer location,
                                                                       @RequestPart(name = "image") MultipartFile multipartFile) {
        return ApiResponse.onSuccess(feedImageService.uploadFeedImage(userId, location, multipartFile));
    }
}
