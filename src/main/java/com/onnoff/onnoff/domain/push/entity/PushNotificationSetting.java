package com.onnoff.onnoff.domain.push.entity;

import com.onnoff.onnoff.domain.push.dto.PushNotificationRequestDTO;
import com.onnoff.onnoff.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PushNotificationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean receivePushNotification;

    private LocalTime pushNotificationTime;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean monday;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean tuesday;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean wednesday;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean thursday;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean friday;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean saturday;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean sunday;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static boolean isMatchingDay(PushNotificationSetting pushNotificationSetting, DayOfWeek today) {
        return switch (today) {
            case MONDAY -> pushNotificationSetting.isMonday();
            case TUESDAY -> pushNotificationSetting.isTuesday();
            case WEDNESDAY -> pushNotificationSetting.isWednesday();
            case THURSDAY -> pushNotificationSetting.isThursday();
            case FRIDAY -> pushNotificationSetting.isFriday();
            case SATURDAY -> pushNotificationSetting.isSaturday();
            case SUNDAY -> pushNotificationSetting.isSunday();
            default -> false;  // 예외 처리 등을 추가할 수 있음
        };
    }

    public void setPushNotification(PushNotificationRequestDTO pushNotificationRequestDTO) {
        this.pushNotificationTime = pushNotificationRequestDTO.getPushNotificationTime();
        this.monday = pushNotificationRequestDTO.isMonday();
        this.tuesday = pushNotificationRequestDTO.isTuesday();
        this.wednesday = pushNotificationRequestDTO.isWednesday();
        this.thursday = pushNotificationRequestDTO.isThursday();
        this.friday = pushNotificationRequestDTO.isFriday();
        this.saturday = pushNotificationRequestDTO.isSaturday();
        this.sunday = pushNotificationRequestDTO.isSunday();
    }
}
