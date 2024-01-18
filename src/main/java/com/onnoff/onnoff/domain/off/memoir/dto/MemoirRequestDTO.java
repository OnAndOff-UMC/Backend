package com.onnoff.onnoff.domain.off.memoir.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemoirRequestDTO {

    @Getter
    public static class WriteDTO {
        @NotNull
        Long userId;
        @NotNull
        LocalDate date;
        @Size(max = 255)
        String icon;
        @NotEmpty
        List<@Valid WriteAnswerDTO> memoirAnswerList;
    }

    @Getter
    public static class WriteAnswerDTO {
        @NotNull
        Long questionId;
        @Size(max = 500)
        String answer;
    }

    @Getter
    public static class UpdateDTO {
        @NotNull
        Long memoirId;
        @Size(max = 255)
        String icon;
        Boolean isBookmarked;
        List<@Valid UpdateAnswerDTO> memoirAnswerList;
    }

    @Getter
    public static class UpdateAnswerDTO {
        @NotNull
        Long answerId;
        @Size(max = 500)
        String answer;
    }
}