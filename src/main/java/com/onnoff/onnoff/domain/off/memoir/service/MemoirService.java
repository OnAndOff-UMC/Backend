package com.onnoff.onnoff.domain.off.memoir.service;

import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MemoirService {
    List<MemoirQuestion> getMemoirQuestion();

    Memoir writeMemoir(MemoirRequestDTO.WriteDTO request);

    Memoir getMemoir(LocalDate date);

    Memoir updateMemoir(MemoirRequestDTO.UpdateDTO request);

    Long deleteMemoir(Long memoirId);
}
