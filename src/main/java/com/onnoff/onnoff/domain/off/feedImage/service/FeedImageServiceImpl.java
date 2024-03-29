package com.onnoff.onnoff.domain.off.feedImage.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.apiPayload.exception.handler.FeedImageHandler;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.off.feedImage.converter.FeedImageConverter;
import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import com.onnoff.onnoff.domain.off.feedImage.entity.FeedImage;
import com.onnoff.onnoff.domain.off.feedImage.repository.FeedImageRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedImageServiceImpl implements FeedImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.path.feed-image}")
    private String path;

    private final AmazonS3Client amazonS3Client;
    private final FeedImageRepository feedImageRepository;
    private final UserService userService;

    @Override
    @Transactional
    public FeedImageResponseDTO.FeedImageDTO uploadFeedImage(MultipartFile multipartFile) {
        Long userId = UserContext.getUserId();
        User user = userService.getUser(userId);

        FeedImage feedImage = FeedImage.builder()
                .user(user)
                .imageKey(uploadImage(multipartFile))
                .build();

        feedImageRepository.save(feedImage);

        return FeedImageConverter.toFeedImageDTO(feedImage, amazonS3Client.getUrl(bucket, feedImage.getImageKey()).toString());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedImageResponseDTO.FeedImageDTO> getFeedImage() {
        Long userId = UserContext.getUserId();
        User user = userService.getUser(userId);
        List<FeedImage> feedImageList = feedImageRepository.findByUserOrderByCreatedAtAsc(user);

        return feedImageList.stream()
                .map(feedImage -> FeedImageConverter.toFeedImageDTO(feedImage, amazonS3Client.getUrl(bucket, feedImage.getImageKey()).toString()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long deleteFeedImage(Long feedImageId) {
        FeedImage feedImage = feedImageRepository.findById(feedImageId).orElseThrow(() -> new FeedImageHandler(ErrorStatus.FEED_IMAGE_NOT_FOUND));

        deleteImage(feedImage.getImageKey());
        feedImageRepository.delete(feedImage);

        return feedImage.getId();
    }

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = path + "/" + createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }

        return fileName;
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new FeedImageHandler(ErrorStatus.FEED_IMAGE_BAD_REQUEST);
        }
    }

    public void deleteImage(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

}
