package com.onnoff.onnoff.domain.off.feedImage.service;

import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedImageService {

    @Transactional
    FeedImageResponseDTO.FeedImageResultDTO uploadFeedImage(Long userId, Integer location, MultipartFile multipartFile);

    List<FeedImageResponseDTO.FeedImageResultDTO> getFeedImage(Long userId);

    FeedImageResponseDTO.FeedImageResultDTO modifyFeedImage(Long feedImageId, MultipartFile multipartFile);

    @Transactional
    Long deleteFeedImage(Long feedImageId);
}
