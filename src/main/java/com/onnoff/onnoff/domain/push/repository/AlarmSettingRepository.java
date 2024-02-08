package com.onnoff.onnoff.domain.push.repository;

import com.onnoff.onnoff.domain.push.entity.AlarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface AlarmSettingRepository extends JpaRepository<AlarmSetting, Long> {
    List<AlarmSetting> findByPushNotificationTimeBetween(LocalTime startTime, LocalTime endTime);
}
