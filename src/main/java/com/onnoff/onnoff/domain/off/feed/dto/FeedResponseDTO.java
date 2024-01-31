package com.onnoff.onnoff.domain.off.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class FeedResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedResultDTO {
        Long feedId;
        LocalDate date;
        String content;
        Boolean isChecked;
    }
}
