package com.onnoff.onnoff.domain.off.feed.repository;

import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findAllByUserAndDateOrderByCreatedAtAsc(User user, LocalDate date);
    Integer countByUserAndDate(User user, LocalDate date);
    Integer countByUserAndDateAndIsChecked(User user, LocalDate date, Boolean t);
}
