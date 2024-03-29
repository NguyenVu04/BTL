package com.project.backend.firebase;

import java.io.FileInputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfiguration {
    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("firebase.json");

        FirebaseOptions options = FirebaseOptions.builder()
                                                 .setCredentials(
                                                    GoogleCredentials.fromStream(serviceAccount)
                                                    )
                                                 .build();
        return FirebaseApp.initializeApp(options);
    }
    @Bean
    public Firestore firestore(FirebaseApp app) {
        return FirestoreClient.getFirestore(app);
    }
}
