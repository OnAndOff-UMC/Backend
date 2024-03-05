package com.onnoff.onnoff.domain.off.feed.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.handler.FeedHandler;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.off.feed.converter.FeedConverter;
import com.onnoff.onnoff.domain.off.feed.dto.FeedRequestDTO;
import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import com.onnoff.onnoff.domain.off.feed.repository.FeedRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Feed addFeed(FeedRequestDTO.AddFeedDTO request) {
        Long userId = UserContext.getUserId();
        User user = userService.getUser(userId);
        return feedRepository.save(FeedConverter.toFeed(request, user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feed> getFeed(LocalDate date) {
        Long userId = UserContext.getUserId();
        User user = userService.getUser(userId);
        return feedRepository.findAllByUserAndDateOrderByCreatedAtAsc(user, date);
    }

    @Override
    @Transactional
    public Feed modifyFeed(Long feedId, FeedRequestDTO.ModifyFeedDTO request) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedHandler(ErrorStatus.FEED_NOT_FOUND));
        feed.setContent(request.getContent().trim());

        return feed;
    }

    @Override
    @Transactional
    public Feed delayFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedHandler(ErrorStatus.FEED_NOT_FOUND));
        feed.setDate(LocalDate.now().plusDays(1));

        return feed;
    }

    @Override
    @Transactional
    public Feed checkFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedHandler(ErrorStatus.FEED_NOT_FOUND));
        feed.setIsChecked(feed.getIsChecked().equals(false));

        return feed;
    }

    @Override
    @Transactional
    public Long deleteFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedHandler(ErrorStatus.FEED_NOT_FOUND));
        feedRepository.delete(feed);

        return feed.getId();
    }
}
