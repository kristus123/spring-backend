package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication

class LOL {
	private void hei() {
		System.out.println("hallo");
	}
}

public class DemoApplication extends  LOL {
	// ${ ENVIRONMENT : dev }

	public static void hei() {

	}



	public static void main(String[] args) {

		System.out.println("og til slutt vil jeg printe");

		//SpringApplication.run(DemoApplication.class, args);
	}
}

