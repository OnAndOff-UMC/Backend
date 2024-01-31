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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/feeds")
    @Operation(summary = "워라벨 피드 조회 API",description = "특정한 날짜의 워라벨 피드를 조회하는 API입니다. Query String으로 사용자 아이디와 날짜를 입력해 주세요.")
    public ApiResponse<List<FeedResponseDTO.FeedResultDTO>> getFeed(@RequestParam(name = "userId") Long userId, @RequestParam(name = "date") LocalDate date){
        List<Feed> feedList = feedService.getFeed(userId, date);
        return ApiResponse.onSuccess(feedList.stream().map(FeedConverter::toFeedResultDTO).toList());
    }

    @PatchMapping("/feeds")
    @Operation(summary = "워라벨 피드 수정 API", description = "기존의 워라벨 피드를 수정하는 API입니다.")
    public ApiResponse<FeedResponseDTO.FeedResultDTO> modifyFeed(@RequestBody @Valid FeedRequestDTO.ModifyFeedDTO request) {
        Feed feed = feedService.modifyFeed(request);
        return ApiResponse.onSuccess(FeedConverter.toFeedResultDTO(feed));
    }

    @DeleteMapping("/feeds/{feedId}")
    @Operation(summary = "워라벨 피드 삭제 API",description = "기존의 워라벨 피드를 삭제하는 API입니다.")
    public ApiResponse<?> deleteFeed(@PathVariable(name = "feedId") Long feedId){
        feedService.deleteFeed(feedId);
        return ApiResponse.onSuccess(null);
    }
}
