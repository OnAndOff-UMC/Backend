package com.onnoff.onnoff.domain.push.service;

import com.google.firebase.messaging.Message;
import com.onnoff.onnoff.domain.push.dto.NotificationMessage;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
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
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void sendGoHomeNotificationByMinute() {
        // Your logic to send the notification every minute
    }

}
