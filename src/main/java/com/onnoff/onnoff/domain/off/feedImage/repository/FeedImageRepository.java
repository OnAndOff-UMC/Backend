package com.onnoff.onnoff.domain.off.feedImage.repository;

import com.onnoff.onnoff.domain.off.feedImage.entity.FeedImage;
import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {

    Optional<FeedImage> findByUserAndLocation(User user, Integer location);
}
