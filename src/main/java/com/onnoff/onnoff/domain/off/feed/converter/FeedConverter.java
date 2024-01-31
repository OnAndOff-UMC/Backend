package com.onnoff.onnoff.domain.off.feed.converter;

import com.onnoff.onnoff.domain.off.feed.dto.FeedRequestDTO;
import com.onnoff.onnoff.domain.off.feed.dto.FeedResponseDTO;
import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import com.onnoff.onnoff.domain.user.User;

public class FeedConverter {

    public static Feed toFeed(FeedRequestDTO.AddFeedDTO request, User user) {
        return Feed.builder()
                .date(request.getDate())
                .content(request.getContent())
                .isChecked(false)
                .user(user)
                .build();
    }

    public static FeedResponseDTO.FeedResultDTO toFeedResultDTO(Feed feed) {
        return FeedResponseDTO.FeedResultDTO.builder()
                .feedId(feed.getId())
                .date(feed.getDate())
                .content(feed.getContent())
                .isChecked(feed.getIsChecked())
                .build();
    }
}
