package com.onnoff.onnoff.domain.off.feed.dto;

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
        @PastOrPresent
        LocalDate date;
        @NotBlank
        @Size(max = 30)
        String content;
    }

    @Getter
    public static class ModifyFeedDTO {
        @NotNull
        Long feedId;
        LocalDate date;
        @Size(max = 30)
        String content;
        Boolean isChecked;
    }
}
