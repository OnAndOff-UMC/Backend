package com.onnoff.onnoff.domain.off.memoir.converter;

import com.onnoff.onnoff.domain.off.memoir.dto.MemoirResponseDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Emoticon;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemoirConverter {

    public static MemoirResponseDTO.MemoirPreviewResultDTO toMemoirPreviewResultDTO(Memoir memoir) {
        if (memoir == null) {
            return MemoirResponseDTO.MemoirPreviewResultDTO.builder()
                    .memoirId(null)
                    .written(false)
                    .build();
        } else {
            return MemoirResponseDTO.MemoirPreviewResultDTO.builder()
                    .memoirId(memoir.getId())
                    .written(true)
                    .build();
        }
    }

    public static MemoirResponseDTO.MemoirResultDTO toMemoirResultDTO(Memoir memoir) {
        return MemoirResponseDTO.MemoirResultDTO.builder()
                .memoirId(memoir.getId())
                .date(memoir.getDate())
                .emoticonUrl(memoir.getEmoticon().getImageUrl())
                .isBookmarked(memoir.getIsBookmarked())
                .memoirAnswerList(toMemoirAnswerResultDTOList(memoir.getMemoirAnswerList()))
                .build();
    }

    public static List<MemoirResponseDTO.MemoirAnswerResultDTO> toMemoirAnswerResultDTOList(List<MemoirAnswer> memoirAnswerList) {
        return memoirAnswerList.stream()
                .map(memoirAnswer -> MemoirResponseDTO.MemoirAnswerResultDTO.builder()
                        .answerId(memoirAnswer.getId())
                        .question(memoirAnswer.getMemoirQuestion().getQuestion())
                        .summary(memoirAnswer.getMemoirQuestion().getSummary())
                        .answer(memoirAnswer.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }

    public static MemoirResponseDTO.BookmarkedMemoirResultListDTO toBookmarkedMemoirPreviewListDTO(Page<Memoir> memoirList) {
        AtomicInteger index = new AtomicInteger();

        List<MemoirResponseDTO.BookmarkedMemoirResultDTO> memoirResultDTOList = memoirList.stream()
                .map(memoir -> MemoirResponseDTO.BookmarkedMemoirResultDTO.builder()
                        .memoirId(memoir.getId())
                        .date(memoir.getDate())
                        .emoticonUrl(memoir.getEmoticon().getImageUrl())
                        .remain(index.getAndIncrement() % 2)
                        .build())
                .toList();

        return MemoirResponseDTO.BookmarkedMemoirResultListDTO.builder()
                .memoirList(memoirResultDTOList)
                .pageNumber(memoirList.getNumber())
                .pageSize(memoirList.getSize())
                .totalPages(memoirList.getTotalPages())
                .totalElements(memoirList.getTotalElements())
                .isFirst(memoirList.isFirst())
                .isLast(memoirList.isLast())
                .build();
    }

    public static List<MemoirResponseDTO.MemoirQuestionResultDTO> toMemoirQuestionResultDTOList(List<MemoirQuestion> memoirQuestionList) {
        return memoirQuestionList.stream()
                .map(memoirQuestion -> MemoirResponseDTO.MemoirQuestionResultDTO.builder()
                        .questionId(memoirQuestion.getId())
                        .question(memoirQuestion.getQuestion())
                        .summary(memoirQuestion.getSummary())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<MemoirResponseDTO.EmoticonResultDTO> toEmoticonResultDTOList(List<Emoticon> emoticonList) {
        return emoticonList.stream()
                .map(emoticon -> MemoirResponseDTO.EmoticonResultDTO.builder()
                        .emoticonId(emoticon.getId())
                        .imageUrl(emoticon.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

}
