package com.example.library_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library_api.model.UserVerification;

// This interface extends JpaRepository to perform CRUD operations for the UserVerification entity
public interface UserVerificationRepository extends JpaRepository<UserVerification, String> {

    // Custom query method to find a user verification record by email
    Optional<UserVerification> findByEmail(String email);
}
