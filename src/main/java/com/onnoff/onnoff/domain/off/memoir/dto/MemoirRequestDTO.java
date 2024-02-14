package com.onnoff.onnoff.domain.off.memoir.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemoirRequestDTO {

    @Getter
    public static class MemoirWriteDTO {
        @NotNull
        @PastOrPresent
        LocalDate date;
        @NotNull
        Long emoticonId;
        @NotNull
        List<@Valid MemoirAnswerDTO> memoirAnswerList;
    }

    @Getter
    public static class MemoirUpdateDTO {
        @NotNull
        Long emoticonId;
        @NotNull
        List<@Valid MemoirAnswerDTO> memoirAnswerList;
    }

    @Getter
    public static class MemoirAnswerDTO {
        @NotNull
        Long questionId;
        @NotNull
        @Size(max = 500)
        String answer;
    }
}
