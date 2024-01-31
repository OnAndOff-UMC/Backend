package com.onnoff.onnoff.domain.off.memoir.converter;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirResponseDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;

import java.util.List;
import java.util.stream.Collectors;

public class MemoirConverter {

    public static List<MemoirResponseDTO.MemoirQuestionResultDTO> toMemoirQuestionResultDTOList(List<MemoirQuestion> memoirQuestionList) {
        return memoirQuestionList.stream()
                .map(x -> MemoirResponseDTO.MemoirQuestionResultDTO.builder()
                        .questionId(x.getId())
                        .question(x.getQuestion())
                        .summary(x.getSummary())
                        .build())
                .collect(Collectors.toList());
    }

    public static Memoir toMemoir(MemoirRequestDTO.MemoirWriteDTO request) {
        return Memoir.builder()
                .date(request.getDate())
                .icon(request.getIcon())
                .isBookmarked(false)
                .build();
    }

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
                .icon(memoir.getIcon())
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

}
