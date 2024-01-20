package com.onnoff.onnoff.domain.off.feedImage.service;

import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedImageService {

    @Transactional
    FeedImageResponseDTO.ResultDTO uploadFeedImage(Long userId, Integer location, MultipartFile multipartFile);

    List<FeedImageResponseDTO.ResultDTO> getFeedImage(Long userId);

    FeedImageResponseDTO.ResultDTO modifyFeedImage(Long feedImageId, MultipartFile multipartFile);
}
