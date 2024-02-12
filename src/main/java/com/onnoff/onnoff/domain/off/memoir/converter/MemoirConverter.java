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

    public static MemoirResponseDTO.MemoirPreviewDTO toMemoirPreviewDTO(Memoir memoir) {
        if (memoir == null) {
            return MemoirResponseDTO.MemoirPreviewDTO.builder()
                    .written(false)
                    .emoticonUrl(null)
                    .build();
        } else {
            return MemoirResponseDTO.MemoirPreviewDTO.builder()
                    .written(true)
                    .emoticonUrl(memoir.getEmoticon().getImageUrl())
                    .build();
        }
    }

    public static MemoirResponseDTO.MemoirDTO toMemoirDTO(Memoir memoir) {
        return MemoirResponseDTO.MemoirDTO.builder()
                .memoirId(memoir.getId())
                .date(memoir.getDate())
                .emoticonUrl(memoir.getEmoticon().getImageUrl())
                .isBookmarked(memoir.getIsBookmarked())
                .memoirAnswerList(toMemoirAnswerDTOList(memoir.getMemoirAnswerList()))
                .build();
    }

    public static List<MemoirResponseDTO.MemoirAnswerDTO> toMemoirAnswerDTOList(List<MemoirAnswer> memoirAnswerList) {
        return memoirAnswerList.stream()
                .map(memoirAnswer -> MemoirResponseDTO.MemoirAnswerDTO.builder()
                        .answerId(memoirAnswer.getId())
                        .question(memoirAnswer.getMemoirQuestion().getQuestion())
                        .summary(memoirAnswer.getMemoirQuestion().getSummary())
                        .answer(memoirAnswer.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }

    public static MemoirResponseDTO.BookmarkedMemoirListDTO toBookmarkedMemoirPreviewListDTO(Page<Memoir> memoirList) {
        AtomicInteger index = new AtomicInteger();

        List<MemoirResponseDTO.BookmarkedMemoirDTO> memoirDTOList = memoirList.stream()
                .map(memoir -> MemoirResponseDTO.BookmarkedMemoirDTO.builder()
                        .date(memoir.getDate())
                        .emoticonUrl(memoir.getEmoticon().getImageUrl())
                        .remain(index.getAndIncrement() % 2)
                        .build())
                .toList();

        return MemoirResponseDTO.BookmarkedMemoirListDTO.builder()
                .memoirList(memoirDTOList)
                .pageNumber(memoirList.getNumber())
                .pageSize(memoirList.getSize())
                .totalPages(memoirList.getTotalPages())
                .totalElements(memoirList.getTotalElements())
                .isFirst(memoirList.isFirst())
                .isLast(memoirList.isLast())
                .build();
    }

    public static List<MemoirResponseDTO.MemoirQuestionDTO> toMemoirQuestionDTOList(List<MemoirQuestion> memoirQuestionList) {
        return memoirQuestionList.stream()
                .map(memoirQuestion -> MemoirResponseDTO.MemoirQuestionDTO.builder()
                        .questionId(memoirQuestion.getId())
                        .question(memoirQuestion.getQuestion())
                        .summary(memoirQuestion.getSummary())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<MemoirResponseDTO.EmoticonDTO> toEmoticonDTOList(List<Emoticon> emoticonList) {
        return emoticonList.stream()
                .map(emoticon -> MemoirResponseDTO.EmoticonDTO.builder()
                        .emoticonId(emoticon.getId())
                        .imageUrl(emoticon.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

}
