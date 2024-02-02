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
        List<@Valid MemoirWriteAnswerDTO> memoirAnswerList;
    }

    @Getter
    public static class MemoirWriteAnswerDTO {
        @NotNull
        Long questionId;
        @Size(max = 500)
        String answer;
    }

    @Getter
    public static class MemoirUpdateDTO {
        @NotNull
        Long emoticonId;
        @NotNull
        List<@Valid MemoirUpdateAnswerDTO> memoirAnswerList;
    }

    @Getter
    public static class MemoirUpdateAnswerDTO {
        @NotNull
        Long answerId;
        @Size(max = 500)
        String answer;
    }
}
