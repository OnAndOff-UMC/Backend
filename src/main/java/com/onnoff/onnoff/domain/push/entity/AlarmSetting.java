package com.onnoff.onnoff.domain.push.entity;

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
public class AlarmSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime pushNotificationTime;

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static boolean isMatchingDay(AlarmSetting alarmSetting, DayOfWeek today) {
        switch (today) {
            case MONDAY:
                return alarmSetting.isMonday();
            case TUESDAY:
                return alarmSetting.isTuesday();
            case WEDNESDAY:
                return alarmSetting.isWednesday();
            case THURSDAY:
                return alarmSetting.isThursday();
            case FRIDAY:
                return alarmSetting.isFriday();
            case SATURDAY:
                return alarmSetting.isSaturday();
            case SUNDAY:
                return alarmSetting.isSunday();
            default:
                return false;  // 예외 처리 등을 추가할 수 있음
        }
    }
}
