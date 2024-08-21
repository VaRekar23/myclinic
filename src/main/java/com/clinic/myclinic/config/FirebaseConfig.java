package com.clinic.myclinic.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {
	@Value("${FIREBASE_SERVICE_ACCOUNT}")
	private String firebaseServiceAccount;
	
	@Bean
	public FirebaseApp initializeFirebase() throws IOException {
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(firebaseServiceAccount.getBytes())))
					.build();
		
			return FirebaseApp.initializeApp(options);
		} else {
            return FirebaseApp.getInstance();
        }
	}
	
	@Bean
    public Firestore getFirestore(FirebaseApp firebaseApp) {
        return FirestoreClient.getFirestore(firebaseApp);
    }
}
