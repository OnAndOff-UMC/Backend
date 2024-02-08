package com.onnoff.onnoff.domain.push.service;

import com.google.firebase.messaging.Message;
import com.onnoff.onnoff.domain.push.dto.NotificationMessage;
import com.onnoff.onnoff.domain.push.entity.AlarmSetting;
import com.onnoff.onnoff.domain.push.repository.AlarmSettingRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.onnoff.onnoff.domain.push.entity.AlarmSetting.isMatchingDay;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmSendNotificationService {
    private final FcmService fcmService;
    private final UserRepository userRepository;
    private final AlarmSettingRepository alarmSettingRepository;
    public void sendTestNotification() {
        NotificationMessage notificationMessage =
                NotificationMessage.toGoHomeNotificationTest();
        final Message message =
                    fcmService.createMessage("testtoken", notificationMessage);
        fcmService.send(message);
    }
    //1분 단위로 푸시알람 보내는 함수
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void sendGoHomeNotificationByMinute() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<AlarmSetting> alarmSettings = alarmSettingRepository.findByPushNotificationTimeBetween(currentTime.minusSeconds(30).toLocalTime(), currentTime.plusSeconds(30).toLocalTime());

        DayOfWeek today = LocalDate.now().getDayOfWeek();
        List<User> users = alarmSettings.stream()
                .filter(alarmSetting -> isMatchingDay(alarmSetting, today))
                .map(AlarmSetting::getUser)
                .collect(Collectors.toList());

        users.forEach(user -> {
            NotificationMessage notificationMessage = NotificationMessage.toGoHomeNotification(user);
            Message message = fcmService.createMessage(user.getFcmToken(), notificationMessage);
            fcmService.send(message);
        });
    }
}
