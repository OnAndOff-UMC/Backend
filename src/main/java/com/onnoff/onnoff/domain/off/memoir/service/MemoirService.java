package com.onnoff.onnoff.domain.off.memoir.service;

import com.onnoff.onnoff.domain.off.memoir.dto.MemoirRequestDTO;
import com.onnoff.onnoff.domain.off.memoir.entity.Emoticon;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MemoirService {

    Memoir writeMemoir(MemoirRequestDTO.MemoirWriteDTO request);

    Memoir getMemoirPreview(LocalDate date);

    Memoir getMemoir(Long memoirId);

    Page<Memoir> getBookmarkedMemoir(Integer pageNumber);

    Memoir modifyMemoir(Long memoirId, MemoirRequestDTO.MemoirUpdateDTO request);

    Memoir bookmarkMemoir(Long memoirId);

    Long deleteMemoir(Long memoirId);

    List<MemoirQuestion> getMemoirQuestion();

    List<Emoticon> getEmoticon();
}
