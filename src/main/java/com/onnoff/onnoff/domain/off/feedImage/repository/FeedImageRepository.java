package com.onnoff.onnoff.domain.off.feedImage.repository;

import com.onnoff.onnoff.domain.off.feedImage.entity.FeedImage;
import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {

    List<FeedImage> findByUserOrderByCreatedAtAsc(User user);
}
