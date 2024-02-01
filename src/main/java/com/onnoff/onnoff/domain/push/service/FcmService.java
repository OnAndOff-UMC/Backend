package com.onnoff.onnoff.domain.push.service;

import com.google.firebase.messaging.*;
import com.onnoff.onnoff.domain.push.dto.NotificationMessage;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Builder
@RequiredArgsConstructor
public class FcmService {
    public void send(Message message) {
        FirebaseMessaging.getInstance()
                .sendAsync(message);
    }
    public Message createMessage(String deviceToken, NotificationMessage notificationMessage) {
        return Message.builder()
                .setToken(deviceToken)
                .setNotification(notificationMessage.toNotification())
                .putData("title", notificationMessage.title())
                .putData("body", notificationMessage.message())
                .putData("type", notificationMessage.type().name())
                .build();
    }

    //다음 행동 유도가 필요할 때 path를 쓴다..!
    public Message createMessage(String deviceToken, NotificationMessage notificationMessage, String path) {

        Notification notification = notificationMessage.toNotification();
        return Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .setApnsConfig(
                        ApnsConfig.builder()
                                .setAps(
                                        Aps.builder()
                                                .setSound("default")
                                                .build()
                                )
                                .build()
                )
                .putData("title", notificationMessage.title())
                .putData("body", notificationMessage.message())
                .putData("type", notificationMessage.type().name())
                .putData("path", path)
                .putData("sound", "default")
                .build();
    }
}
