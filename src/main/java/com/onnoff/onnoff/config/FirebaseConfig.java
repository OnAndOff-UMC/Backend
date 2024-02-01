package com.onnoff.onnoff.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class FirebaseConfig {
    private final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);
    @Value("${fcm.firebase-sdk-path}") // your firebase sdk path
    private String firebaseSdkPath;
    @Value("${fcm.project-id}")
    private String projectId;
    @PostConstruct
    public void initialize() {
        try {
            ClassPathResource resource = new ClassPathResource(firebaseSdkPath);
            InputStream serviceAccount = resource.getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Successfully firebase application initialized!");
            }
        } catch (FileNotFoundException e) {
            logger.error("Firebase ServiceAccountKey FileNotFoundException" + e.getMessage());
        } catch (IOException e) {
            logger.error("FirebaseOptions IOException" + e.getMessage());
        }
    }
}
