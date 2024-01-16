package com.onnoff.onnoff.domain.off.memoir.repository;

import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoirRepository extends JpaRepository<Memoir, Long> {
}
