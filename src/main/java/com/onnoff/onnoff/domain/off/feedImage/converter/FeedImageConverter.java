package com.onnoff.onnoff.domain.off.feedImage.converter;

import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import com.onnoff.onnoff.domain.off.feedImage.entity.FeedImage;

public class FeedImageConverter {

    public static FeedImageResponseDTO.FeedImageDTO toFeedImageDTO(FeedImage feedImage, String imageUrl) {
        return FeedImageResponseDTO.FeedImageDTO.builder()
                .feedImageId(feedImage.getId())
                .imageUrl(imageUrl)
                .build();
    }
}
