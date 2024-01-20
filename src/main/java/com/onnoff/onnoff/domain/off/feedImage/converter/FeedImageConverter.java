package com.onnoff.onnoff.domain.off.feedImage.converter;

import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import com.onnoff.onnoff.domain.off.feedImage.entity.FeedImage;

public class FeedImageConverter {

    public static FeedImageResponseDTO.FeedImageResultDTO toResultDTO(FeedImage feedImage, String imageUrl) {
        return FeedImageResponseDTO.FeedImageResultDTO.builder()
                .feedImageId(feedImage.getId())
                .location(feedImage.getLocation())
                .imageUrl(imageUrl)
                .build();
    }
}
