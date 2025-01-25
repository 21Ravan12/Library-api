package com.example.library_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main application class for starting the Spring Boot application
@SpringBootApplication
public class LibraryApiApplication {

    // Main method to run the Spring Boot application
	public static void main(String[] args) {
        // Runs the Spring Boot application, starting the application context and all necessary configurations
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
