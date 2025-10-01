package com.swe307.first_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class FirstProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstProjectApplication.class, args);
	}
}
