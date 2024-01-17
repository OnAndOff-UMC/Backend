package com.onnoff.onnoff.domain.off.memoir.converter;

import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.dto.MemoirResponseDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;

public class MemoirConverter {

    public static MemoirResponseDTO.ResultDTO toResultDTO(Memoir memoir) {
        return MemoirResponseDTO.ResultDTO.builder()
                .memoirId(memoir.getId())
                .date(memoir.getDate())
                .icon(memoir.getIcon())
                .bookmarked(memoir.getBookmarked())
                .memoirAnswerList(MemoirAnswerConverter.toResultListDTO(memoir.getMemoirAnswerList()))
                .build();
    }

    public static Memoir toMemoir(MemoirRequestDTO.WriteDTO request) {
        return Memoir.builder()
                .date(request.getDate())
                .icon(request.getIcon())
                .bookmarked(false)
                .build();
    }
}
