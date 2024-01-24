package com.onnoff.onnoff.domain.off.feed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

public class FeedRequestDTO {

    @Getter
    public static class AddFeedDTO {
        @NotNull
        Long userId;
        @NotNull
        @PastOrPresent
        LocalDate date;
        @NotBlank
        @Size(max = 30)
        String content;
    }
}
