package com.onnoff.onnoff.domain.user.repository;

import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauthId);

}
