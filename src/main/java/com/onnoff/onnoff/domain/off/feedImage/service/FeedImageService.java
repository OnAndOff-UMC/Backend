package com.onnoff.onnoff.domain.off.feedImage.service;

import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface FeedImageService {

    @Transactional
    FeedImageResponseDTO.ResultDTO uploadFeedImage(Long userId, Integer location, MultipartFile multipartFile);
}
