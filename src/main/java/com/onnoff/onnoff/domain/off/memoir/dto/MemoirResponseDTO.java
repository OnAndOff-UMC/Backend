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
    public static class QuestionResultDTO{
        Long questionId;
        String question;
        String summary;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultDTO {
        Long memoirId;
        LocalDate date;
        String icon;
        Boolean bookmarked;
        List<AnswerResultDTO> memoirAnswerList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerResultDTO{
        Long answerId;
        String question;
        String summary;
        String answer;
    }

}
