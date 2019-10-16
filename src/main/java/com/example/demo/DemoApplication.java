package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	// ${ ENVIRONMENT : dev }
	

	public static void main(String[] args) {
		short s =  5;
		SpringApplication.run(DemoApplication.class, args);
	}
}

