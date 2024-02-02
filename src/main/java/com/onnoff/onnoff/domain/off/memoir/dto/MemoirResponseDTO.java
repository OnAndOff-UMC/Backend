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
    public static class MemoirDTO {
        Long memoirId;
        LocalDate date;
        String emoticonUrl;
        Boolean isBookmarked;
        List<MemoirAnswerDTO> memoirAnswerList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoirAnswerDTO {
        Long answerId;
        String question;
        String summary;
        String answer;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoirPreviewDTO {
        Boolean written;
        Long memoirId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkedMemoirListDTO {
        List<BookmarkedMemoirDTO> memoirList;
        Integer pageNumber;
        Integer pageSize;
        Integer totalPages;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkedMemoirDTO {
        Long memoirId;
        LocalDate date;
        String emoticonUrl;
        Integer remain;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoirQuestionDTO {
        Long questionId;
        String question;
        String summary;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmoticonDTO {
        Long emoticonId;
        String imageUrl;
    }

}
