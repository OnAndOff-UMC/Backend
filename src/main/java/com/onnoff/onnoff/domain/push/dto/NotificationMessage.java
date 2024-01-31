package com.onnoff.onnoff.domain.push.dto;

import com.google.firebase.messaging.Notification;
import com.onnoff.onnoff.domain.user.User;
import lombok.Builder;

@Builder
public record NotificationMessage(
        String title,
        String message,
        NotificationType type
) {
    public static NotificationMessage toGoHomeNotification(User user) {
        return NotificationMessage.builder()
                .title(user.getName() + "님, 이제 퇴근하실 시간이에요.")
                .message("")
                .type(NotificationType.NOTIFY)
                .build();
    }
    public static NotificationMessage toGoHomeNotificationTest() {
        return NotificationMessage.builder()
                .title("예진" + "님, 이제 퇴근하실 시간이에요.")
                .message("")
                .type(NotificationType.NOTIFY)
                .build();
    }


    public Notification toNotification() {
        return Notification.builder()
                .setTitle(title)
                .setBody(message)
                .build();
    }
}
