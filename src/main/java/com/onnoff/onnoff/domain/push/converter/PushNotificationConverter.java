package com.onnoff.onnoff.domain.push.converter;

import com.onnoff.onnoff.domain.push.dto.PushNotificationSettingRequestDTO;
import com.onnoff.onnoff.domain.push.dto.PushNotificationSettingResponseDTO;
import com.onnoff.onnoff.domain.push.entity.PushNotificationSetting;
import com.onnoff.onnoff.domain.user.User;

import java.time.LocalTime;

public class PushNotificationConverter {
    public static PushNotificationSetting toPushNotificationSetting(PushNotificationSettingRequestDTO pushNotificationSettingRequestDTO, User user) {
        return PushNotificationSetting.builder()
                .pushNotificationTime((pushNotificationSettingRequestDTO.getPushNotificationTime() != null) ? pushNotificationSettingRequestDTO.getPushNotificationTime() : LocalTime.of(0, 0))
                .receivePushNotification(pushNotificationSettingRequestDTO.isReceivePushNotification())
                .monday(pushNotificationSettingRequestDTO.isMonday())
                .tuesday(pushNotificationSettingRequestDTO.isTuesday())
                .wednesday(pushNotificationSettingRequestDTO.isWednesday())
                .thursday(pushNotificationSettingRequestDTO.isThursday())
                .friday(pushNotificationSettingRequestDTO.isFriday())
                .saturday(pushNotificationSettingRequestDTO.isSaturday())
                .sunday(pushNotificationSettingRequestDTO.isSunday())
                .user(user)
                .build();
    }
    public static PushNotificationSettingResponseDTO toPushNotificationResponseDTO(PushNotificationSetting pushNotificationSetting) {
        return PushNotificationSettingResponseDTO.builder()
                .receivePushNotification(pushNotificationSetting.isReceivePushNotification())
                .pushNotificationTime(pushNotificationSetting.getPushNotificationTime())
                .monday(pushNotificationSetting.isMonday())
                .tuesday(pushNotificationSetting.isTuesday())
                .wednesday(pushNotificationSetting.isWednesday())
                .thursday(pushNotificationSetting.isThursday())
                .friday(pushNotificationSetting.isFriday())
                .saturday(pushNotificationSetting.isSaturday())
                .sunday(pushNotificationSetting.isSunday())
                .build();
    }
}
