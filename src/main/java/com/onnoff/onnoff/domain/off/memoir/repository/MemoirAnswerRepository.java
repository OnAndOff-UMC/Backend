package com.onnoff.onnoff.domain.off.memoir.repository;

import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoirAnswerRepository extends JpaRepository<MemoirAnswer, Long> {
    List<MemoirAnswer> findAllByMemoirId(Long memoirid);

}
