package com.example.springbootweb3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootWeb3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWeb3Application.class, args);
	}

}
