package com.onnoff.onnoff.domain.on.worklog.repository;

import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;
import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorklogRepository extends JpaRepository<Worklog, Long> {
    List<Worklog> findAllByUserAndDateOrderByCreatedAt(User user, LocalDate date);
    Integer countByUserAndDate(User user, LocalDate date);
    Integer countByUserAndDateAndIsChecked(User user, LocalDate date, Boolean t);

}
