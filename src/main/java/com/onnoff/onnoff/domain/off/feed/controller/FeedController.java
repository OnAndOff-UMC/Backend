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
    public ApiResponse<FeedResponseDTO.FeedDTO> addFeed(@RequestBody @Valid FeedRequestDTO.AddFeedDTO request) {
        Feed feed = feedService.addFeed(request);
        return ApiResponse.onSuccess(FeedConverter.toFeedDTO(feed));
    }

    @GetMapping("/feeds")
    @Operation(summary = "워라벨 피드 조회 API", description = "특정한 날짜의 워라벨 피드를 조회하는 API입니다. Query String으로 날짜를 입력해 주세요.")
    public ApiResponse<List<FeedResponseDTO.FeedDTO>> getFeed(@RequestParam(name = "date") LocalDate date) {
        List<Feed> feedList = feedService.getFeed(date);
        return ApiResponse.onSuccess(feedList.stream().map(FeedConverter::toFeedDTO).toList());
    }

    @PatchMapping("/feeds/{feedId}")
    @Operation(summary = "워라벨 피드 내용 수정 API", description = "워라벨 피드의 내용을 수정하는 API입니다.")
    public ApiResponse<FeedResponseDTO.FeedDTO> modifyFeed(@PathVariable(name = "feedId") Long feedId, @RequestBody @Valid FeedRequestDTO.ModifyFeedDTO request) {
        Feed feed = feedService.modifyFeed(feedId, request);
        return ApiResponse.onSuccess(FeedConverter.toFeedDTO(feed));
    }

    @PatchMapping("/feeds/{feedId}/delay")
    @Operation(summary = "워라벨 피드 내일로 미루기 API", description = "워라벨 피드의 날짜를 현재 기준 내일로 변경하는 API입니다.")
    public ApiResponse<FeedResponseDTO.FeedDTO> delayFeed(@PathVariable(name = "feedId") Long feedId) {
        Feed feed = feedService.delayFeed(feedId);
        return ApiResponse.onSuccess(FeedConverter.toFeedDTO(feed));
    }

    @PatchMapping("/feeds/{feedId}/check")
    @Operation(summary = "워라벨 피드 체크 및 해제 API", description = "워라벨 피드를 체크하거나 체크 해제하는 API입니다.")
    public ApiResponse<FeedResponseDTO.FeedDTO> checkFeed(@PathVariable(name = "feedId") Long feedId) {
        Feed feed = feedService.checkFeed(feedId);
        return ApiResponse.onSuccess(FeedConverter.toFeedDTO(feed));
    }

    @DeleteMapping("/feeds/{feedId}")
    @Operation(summary = "워라벨 피드 삭제 API", description = "기존의 워라벨 피드를 삭제하는 API입니다.")
    public ApiResponse<Long> deleteFeed(@PathVariable(name = "feedId") Long feedId) {
        return ApiResponse.onSuccess(feedService.deleteFeed(feedId));
    }
}
