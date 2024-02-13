package com.onnoff.onnoff.domain.off.memoir.repository;

import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoirAnswerRepository extends JpaRepository<MemoirAnswer, Long> {

    Optional<MemoirAnswer> findByMemoirAndMemoirQuestion(Memoir memoir, MemoirQuestion memoirQuestion);

    List<MemoirAnswer> findAllByMemoirId(Long memoirid);
}
