package com.ChaTop.Rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Acts as a main class for Spring Boot application : bootstrapes and launches the Spring application
 * To achieve this, automatically creates the ApplicationContext, scan the configuration classes
 */
@SpringBootApplication
public class RentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalApplication.class, args);
	}

}
