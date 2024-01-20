package com.onnoff.onnoff.domain.off.feedImage.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.domain.off.feedImage.converter.FeedImageConverter;
import com.onnoff.onnoff.domain.off.feedImage.dto.FeedImageResponseDTO;
import com.onnoff.onnoff.domain.off.feedImage.entity.FeedImage;
import com.onnoff.onnoff.domain.off.feedImage.repository.FeedImageRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    private final AmazonS3Client amazonS3Client;

    private final FeedImageRepository feedImageRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public FeedImageResponseDTO.FeedImageResultDTO uploadFeedImage(Long userId, Integer location, MultipartFile multipartFile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        if (feedImageRepository.findByUserAndLocation(user, location).isPresent()) {
            throw new GeneralException(ErrorStatus.FEED_IMAGE_EXIST);
        }

        if (location < 1 | location > 9) {
            throw new GeneralException(ErrorStatus.FEED_IMAGE_LOCATION_INVALID);
        }

        FeedImage feedImage = FeedImage.builder()
                .user(user)
                .location(location)
                .imageKey(uploadImage(multipartFile))
                .build();

        feedImageRepository.save(feedImage);

        return FeedImageConverter.toResultDTO(feedImage, amazonS3Client.getUrl(bucket, feedImage.getImageKey()).toString());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedImageResponseDTO.FeedImageResultDTO> getFeedImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        List<FeedImage> feedImageList = feedImageRepository.findByUserOrderByLocationAsc(user);

        return feedImageList.stream()
                .map(feedImage -> FeedImageConverter.toResultDTO(feedImage, amazonS3Client.getUrl(bucket, feedImage.getImageKey()).toString()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FeedImageResponseDTO.FeedImageResultDTO modifyFeedImage(Long feedImageId, MultipartFile multipartFile) {
        FeedImage feedImage = feedImageRepository.findById(feedImageId).orElseThrow(() -> new GeneralException(ErrorStatus.FEED_IMAGE_NOT_FOUND));

        deleteImage(feedImage.getImageKey());
        feedImage.setImageKey(uploadImage(multipartFile));

        return FeedImageConverter.toResultDTO(feedImage, amazonS3Client.getUrl(bucket, feedImage.getImageKey()).toString());
    }

    @Override
    @Transactional
    public Long deleteFeedImage(Long feedImageId) {
        FeedImage feedImage = feedImageRepository.findById(feedImageId).orElseThrow(() -> new GeneralException(ErrorStatus.FEED_IMAGE_NOT_FOUND));

        deleteImage(feedImage.getImageKey());
        feedImageRepository.delete(feedImage);

        return feedImage.getId();
    }

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    public void deleteImage(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

}
