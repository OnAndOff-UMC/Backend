package com.onnoff.onnoff.domain.off.memoir.repository;

import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemoirRepository extends JpaRepository<Memoir, Long> {

    Optional<Memoir> findByUserAndDate(User user, LocalDate date);

    Page<Memoir> findByUserAndIsBookmarkedOrderByDateDesc(User user, Boolean isBookmarked, PageRequest pageRequest);
}
