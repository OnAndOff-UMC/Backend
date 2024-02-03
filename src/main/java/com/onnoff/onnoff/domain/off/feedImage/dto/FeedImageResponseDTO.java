package com.onnoff.onnoff.domain.off.feedImage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedImageResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedImageDTO {
        Long feedImageId;
        String imageUrl;
    }
}
