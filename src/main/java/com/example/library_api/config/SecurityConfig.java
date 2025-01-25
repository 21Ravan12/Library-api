package com.example.library_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // SecurityFilterChain Bean provides the security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disabling CSRF (Cross-Site Request Forgery) protection
            // CSRF protection is usually unnecessary for stateless (REST) applications.
            // It is important in traditional web applications, but API-based applications often use other methods like tokens for security.
            .csrf(csrf -> csrf.disable()) 

            // Security configurations for HTTP requests
            .authorizeHttpRequests(auth -> auth
                // Grant anonymous access to the following URLs
                // These endpoints can be accessed without authentication
                .requestMatchers(
                    "/api/users/register", // User registration
                    "/api/users/verifyForRegister", // User verification
                    "/api/users/login", // User login
                    "/api/users/changeCode", // Send password change code
                    "/api/users/verifyForChangeCode", // Verify password change code
                    "/api/users/changeCodeEnd", // Complete password change
                    "/api/users/giveTierAndRetract", // Give or retract user tier
                    "/api/users/update", // Update user details
                    "/api/users/search", // Search users
                    "/api/users/delete", // Delete user
                    "/api/books/add", // Add a book
                    "/api/books/getAll", // Get all books
                    "/api/books/search", // Search books
                    "/api/books/delete", // Delete a book
                    "/api/books/update", // Update book details
                    "/api/reservations/reserve", // Reserve a book
                    "/api/reservations/search", // Search reservations
                    "/api/reservations/delete", // Delete reservation
                    "/api/reservations/update", // Update reservation
                    "/api/loans/borrow", // Borrow a book
                    "/api/loans/return", // Return a book
                    "/api/loans/search", // Search loaned books
                    "/api/loans/update", // Update loan details
                    "/api/comments/add", // Add a comment
                    "/api/comments/delete", // Delete a comment
                    "/api/comments/search", // Search comments
                    "/api/comments/update", // Update comment
                    "/api/categories/add", // Add category
                    "/api/categories/update", // Update category
                    "/api/categories/search", // Search categories
                    "/api/categories/delete" // Delete category
                ).permitAll() // Allow everyone to access these endpoints without authentication

                // Set all other endpoints to require authentication
                // All other endpoints that are not listed above will require authentication
                .anyRequest().authenticated() // All other requests require authentication
            );

        // Finalize the configuration and return the security filter chain
        return http.build(); // Complete the configuration
    }
}
