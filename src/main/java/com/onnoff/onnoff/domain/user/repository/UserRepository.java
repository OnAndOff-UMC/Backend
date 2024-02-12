package com.onnoff.onnoff.domain.user.repository;

import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauthId);

    Optional<User> findByNickname(String nickname);

    List<User> findByStatusAndInactiveDateBefore(Status status, LocalDateTime oneMonthAgo);
}
