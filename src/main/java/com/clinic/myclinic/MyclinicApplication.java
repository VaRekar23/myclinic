package com.clinic.myclinic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MyclinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyclinicApplication.class, args);
	}

}
