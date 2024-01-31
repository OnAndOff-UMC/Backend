package com.onnoff.onnoff.domain.push.controller;

import com.onnoff.onnoff.domain.push.service.FcmSendNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FcmController {
    private final FcmSendNotificationService fcmSendNotificationService;

    @GetMapping("/message/test")
    public ResponseEntity pushTest(){
        fcmSendNotificationService.sendTestNotification();
        return ResponseEntity.ok("good");
    }
}
