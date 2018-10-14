package com.fatcup.backend;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@SpringBootApplication
@EnableAutoConfiguration
public class FatcupApplication {

	public static void main(String[] args) {
		SpringApplication.run(FatcupApplication.class, args);
	}

	@Bean
	public FirebaseAuth firebaseAuth() throws IOException {

		InputStream serviceAccount = new ClassPathResource("firebase-adminsdk.json").getInputStream();

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://fatcup-216514.firebaseio.com").build();

		FirebaseApp.initializeApp(options);

		return FirebaseAuth.getInstance();
	}
}
