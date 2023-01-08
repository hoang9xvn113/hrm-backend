package com.datn.hrm.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FCMInitializer {

    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() throws IOException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(
                        GoogleCredentials.fromStream(
                                new ClassPathResource(
                                        "./firebase-credentials.json"
                                ).getInputStream()
                        )
                ).build();

        if (FirebaseApp.getApps().isEmpty()) {
            this.firebaseApp = FirebaseApp.initializeApp(options);
        } else {
            this.firebaseApp = FirebaseApp.getInstance();
        }
    }
}
