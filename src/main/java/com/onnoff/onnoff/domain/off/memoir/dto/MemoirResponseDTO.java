package com.onnoff.onnoff.domain.off.memoir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MemoirResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoirQuestionResultDTO{
        Long questionId;
        String question;
        String summary;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoirResultDTO {
        Long memoirId;
        LocalDate date;
        String icon;
        Boolean isBookmarked;
        List<MemoirAnswerResultDTO> memoirAnswerList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoirAnswerResultDTO{
        Long answerId;
        String question;
        String summary;
        String answer;
    }

}
