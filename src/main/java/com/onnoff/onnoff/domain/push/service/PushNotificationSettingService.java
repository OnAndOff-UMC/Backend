package com.onnoff.onnoff.domain.push.service;

import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.push.converter.PushNotificationConverter;
import com.onnoff.onnoff.domain.push.dto.PushNotificationSettingRequestDTO;
import com.onnoff.onnoff.domain.push.dto.PushNotificationSettingResponseDTO;
import com.onnoff.onnoff.domain.push.entity.PushNotificationSetting;
import com.onnoff.onnoff.domain.push.repository.PushNotificationSettingRepository;
import com.onnoff.onnoff.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PushNotificationSettingService {
    public final PushNotificationSettingRepository pushNotificationSettingRepository;

    @Transactional
    public PushNotificationSettingResponseDTO setPushNotification(PushNotificationSettingRequestDTO pushNotificationSettingRequestDTO) {
        User user = UserContext.getUser();
        PushNotificationSetting pushNotificationSetting = pushNotificationSettingRepository.findByUser(user)
                .orElseGet(() -> PushNotificationConverter.toPushNotificationSetting(pushNotificationSettingRequestDTO, user));
        if (pushNotificationSetting.getId() != null) {
            // 기존에 존재하는 설정인 경우에만 업데이트
            pushNotificationSetting.setPushNotification(pushNotificationSettingRequestDTO);
        }
        pushNotificationSettingRepository.save(pushNotificationSetting);
        return PushNotificationConverter.toPushNotificationResponseDTO(pushNotificationSetting);
    }


    @Transactional(readOnly = true)
    public PushNotificationSettingResponseDTO getPushNotification() {
        User user = UserContext.getUser();
        Optional<PushNotificationSetting> pushNotificationSetting = pushNotificationSettingRepository.findByUser(user);
        if (pushNotificationSetting.isPresent()) {
            return PushNotificationConverter.toPushNotificationResponseDTO(pushNotificationSetting.get());
        } else {
            return PushNotificationConverter.toPushNotificationResponseDTO(PushNotificationSetting.builder().
                    receivePushNotification(false).
                    build());
        }
    }
}
