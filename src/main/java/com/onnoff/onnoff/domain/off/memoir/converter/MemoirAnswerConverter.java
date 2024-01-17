package com.onnoff.onnoff.domain.off.memoir.converter;

import com.onnoff.onnoff.domain.off.memoir.dto.MemoirAnswerResponseDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;

import java.util.List;
import java.util.stream.Collectors;

public class MemoirAnswerConverter {

    public static List<MemoirAnswerResponseDTO.ResultDTO> toResultListDTO(List<MemoirAnswer> memoirAnswerList) {
        return memoirAnswerList.stream()
                .map(memoirAnswer -> MemoirAnswerResponseDTO.ResultDTO.builder()
                        .answerId(memoirAnswer.getId())
                        .question(memoirAnswer.getMemoirQuestion().getQuestion())
                        .summary(memoirAnswer.getMemoirQuestion().getSummary())
                        .answer(memoirAnswer.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }
}
