package com.onnoff.onnoff.domain.off.memoir.service;

import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;

import java.util.List;

public interface MemoirService {
    List<MemoirQuestion> getMemoirQuestion(Long userId);
}
