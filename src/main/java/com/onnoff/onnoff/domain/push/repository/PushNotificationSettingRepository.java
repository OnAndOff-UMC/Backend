package com.onnoff.onnoff.domain.push.repository;

import com.onnoff.onnoff.domain.push.entity.PushNotificationSetting;
import com.onnoff.onnoff.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface PushNotificationSettingRepository extends JpaRepository<PushNotificationSetting, Long> {
    List<PushNotificationSetting> findByPushNotificationTimeBetween(LocalTime startTime, LocalTime endTime);

    Optional<PushNotificationSetting> findByUser(User user);
}
