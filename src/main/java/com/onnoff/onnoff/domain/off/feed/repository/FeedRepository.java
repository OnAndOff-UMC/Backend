package com.onnoff.onnoff.domain.off.feed.repository;

import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
