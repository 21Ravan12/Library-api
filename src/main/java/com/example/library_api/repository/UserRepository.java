package com.example.library_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library_api.model.User;

// This interface extends JpaRepository to perform CRUD operations for the User entity
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method to find a user by their email address
    Optional<User> findByEmail(String email);
    
    // Checks if a user with the given email exists
    boolean existsByEmail(String email);

    // Custom query method to find a user by their name
    Optional<User> findByName(String name);
    
    // Custom query method to find a user by their birth year
    Optional<User> findByBirthyear(Integer birthyear);
    
    // Custom query method to find a user by their tier level
    Optional<User> findByTier(String tier);
}
