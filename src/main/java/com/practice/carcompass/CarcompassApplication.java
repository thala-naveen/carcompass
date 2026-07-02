package com.practice.carcompass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mongock.runner.springboot.EnableMongock;

@EnableMongock
@SpringBootApplication
public class CarcompassApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarcompassApplication.class, args);
	}

}
