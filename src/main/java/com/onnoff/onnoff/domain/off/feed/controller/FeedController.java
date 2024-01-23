package com.onnoff.onnoff.domain.off.feed.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.off.feed.converter.FeedConverter;
import com.onnoff.onnoff.domain.off.feed.dto.FeedRequestDTO;
import com.onnoff.onnoff.domain.off.feed.dto.FeedResponseDTO;
import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import com.onnoff.onnoff.domain.off.feed.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/feeds")
    @Operation(summary = "워라벨 피드 추가 API", description = "새로운 워라벨 피드를 추가하는 API입니다.")
    public ApiResponse<FeedResponseDTO.FeedResultDTO> addFeed(@RequestBody @Valid FeedRequestDTO.AddFeedDTO request) {
        Feed feed = feedService.addFeed(request);
        return ApiResponse.onSuccess(FeedConverter.toFeedResultDTO(feed));
    }
}
