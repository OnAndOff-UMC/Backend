package com.onnoff.onnoff.domain.off.feed.service;

import com.onnoff.onnoff.domain.off.feed.dto.FeedRequestDTO;
import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface FeedService {

    Feed addFeed(FeedRequestDTO.AddFeedDTO request);

    List<Feed> getFeed(Long userId, LocalDate date);
}
