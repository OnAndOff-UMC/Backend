package com.onnoff.onnoff.domain.off.memoir.converter;

import com.onnoff.onnoff.domain.off.memoir.dto.MemoirQuestionResponseDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;

import java.util.List;
import java.util.stream.Collectors;

public class MemoirQuestionConverter {

    public static List<MemoirQuestionResponseDTO.GetResultDTO> toGetResultDTO(List<MemoirQuestion> memoirQuestionList) {
        return memoirQuestionList.stream()
                .map(x -> MemoirQuestionResponseDTO.GetResultDTO.builder()
                        .questionId(x.getId())
                        .question(x.getQuestion())
                        .summary(x.getSummary())
                        .build())
                .collect(Collectors.toList());
    }
}
