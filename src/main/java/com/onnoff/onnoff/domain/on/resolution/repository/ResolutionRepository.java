package com.onnoff.onnoff.domain.on.resolution.repository;

import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ResolutionRepository extends JpaRepository<Resolution, Long> {
    List<Resolution> findAllByUserAndDate(User user, LocalDate date);

    Long countByUserAndDate(User user, LocalDate date);
}
