package com.onnoff.onnoff.domain.off.memoir.converter;

import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirResponseDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;

import java.util.List;
import java.util.stream.Collectors;

public class MemoirConverter {

    public static List<MemoirResponseDTO.QuestionResultDTO> toQuestionResultDTOList(List<MemoirQuestion> memoirQuestionList) {
        return memoirQuestionList.stream()
                .map(x -> MemoirResponseDTO.QuestionResultDTO.builder()
                        .questionId(x.getId())
                        .question(x.getQuestion())
                        .summary(x.getSummary())
                        .build())
                .collect(Collectors.toList());
    }

    public static Memoir toMemoir(MemoirRequestDTO.WriteDTO request) {
        return Memoir.builder()
                .date(request.getDate())
                .icon(request.getIcon())
                .bookmarked(false)
                .build();
    }

    public static MemoirResponseDTO.ResultDTO toResultDTO(Memoir memoir) {
        return MemoirResponseDTO.ResultDTO.builder()
                .memoirId(memoir.getId())
                .date(memoir.getDate())
                .icon(memoir.getIcon())
                .bookmarked(memoir.getBookmarked())
                .memoirAnswerList(toAnswerResultDTOList(memoir.getMemoirAnswerList()))
                .build();
    }

    public static List<MemoirResponseDTO.AnswerResultDTO> toAnswerResultDTOList(List<MemoirAnswer> memoirAnswerList) {
        return memoirAnswerList.stream()
                .map(memoirAnswer -> MemoirResponseDTO.AnswerResultDTO.builder()
                        .answerId(memoirAnswer.getId())
                        .question(memoirAnswer.getMemoirQuestion().getQuestion())
                        .summary(memoirAnswer.getMemoirQuestion().getSummary())
                        .answer(memoirAnswer.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }
}
