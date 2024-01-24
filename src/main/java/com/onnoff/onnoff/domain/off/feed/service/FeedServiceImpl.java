package com.onnoff.onnoff.domain.off.feed.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.domain.off.feed.converter.FeedConverter;
import com.onnoff.onnoff.domain.off.feed.dto.FeedRequestDTO;
import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import com.onnoff.onnoff.domain.off.feed.repository.FeedRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Feed addFeed(FeedRequestDTO.AddFeedDTO request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        return feedRepository.save(FeedConverter.toFeed(request, user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feed> getFeed(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        return feedRepository.findAllByUserAndDateOrderByCreatedAtAsc(user, date);
    }

    @Override
    @Transactional
    public Feed modifyFeed(FeedRequestDTO.ModifyFeedDTO request) {
        Feed feed = feedRepository.findById(request.getFeedId()).orElseThrow(() -> new GeneralException(ErrorStatus.FEED_NOT_FOUND));
        if (request.getContent() != null && request.getContent().trim().isEmpty()) {
            throw new GeneralException(ErrorStatus.FEED_NOT_BLANK);
        }
        feed.updateFeed(request.getDate(), request.getContent(), request.getIsChecked());
        return feed;
    }
}
