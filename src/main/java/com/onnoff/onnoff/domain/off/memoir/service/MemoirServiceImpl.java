package com.onnoff.onnoff.domain.off.memoir.service;

import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoirServiceImpl implements MemoirService {

    private final MemoirQuestionRepository memoirQuestionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemoirQuestion> getMemoirQuestion(Long userId) {
        return memoirQuestionRepository.findAll();
    }
}
