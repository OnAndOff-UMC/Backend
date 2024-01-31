package com.onnoff.onnoff.domain.push.service;

import com.google.firebase.messaging.Message;
import com.onnoff.onnoff.domain.push.dto.NotificationMessage;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmSendNotificationService {
    private final FcmService fcmService;
    private final UserRepository userRepository;
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
        LocalTime startTime = currentTime.minusSeconds(30).toLocalTime();
        LocalTime endTime = currentTime.plusSeconds(30).toLocalTime();
        List<User> userList = userRepository.findByPushNotificationTimeBetween(startTime, endTime);
        for (User user : userList) {
            NotificationMessage notificationMessage = NotificationMessage.toGoHomeNotification(user);
            Message message = fcmService.createMessage(user.getFcmToken(), notificationMessage);
            fcmService.send(message);
        }
    }

}
