package com.onnoff.onnoff.domain.off.feedImage.service;

import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedImageService {

    FeedImageResponseDTO.FeedImageDTO uploadFeedImage(MultipartFile multipartFile);

    List<FeedImageResponseDTO.FeedImageDTO> getFeedImage();

    Long deleteFeedImage(Long feedImageId);
}
