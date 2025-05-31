package com.example.Titan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TitanApplication {

	public static void main(String[] args) {
		System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "C:/Users/raj98/Downloads/watch-label.json");
		SpringApplication.run(TitanApplication.class, args);
	}

}
