package com.example.library_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // Marks this class as an entity for JPA (Java Persistence API), which corresponds to a table in the database
public class UserVerification {

    @Id // Specifies that the email is the unique identifier (primary key) for the user verification
    private String email;  // The user's email address, used as the primary key for verification

    private String code;  // The verification code sent to the user's email

    private String password;  // The user's password (for verification purposes)

    private String name;  // The user's name (used during the verification process)

    private int birthYear;  // The user's birth year (used during the verification process)

    // Getter and Setter methods

    public String getEmail() {
        return email;  // Returns the user's email address
    }

    public void setEmail(String email) {
        this.email = email;  // Sets the user's email address
    }

    public String getCode() {
        return code;  // Returns the verification code
    }

    public void setCode(String code) {
        this.code = code;  // Sets the verification code
    }

    public String getPassword() {
        return password;  // Returns the user's password
    }

    public void setPassword(String password) {
        this.password = password;  // Sets the user's password
    }

    public String getName() {
        return name;  // Returns the user's name
    }

    public void setName(String name) {
        this.name = name;  // Sets the user's name
    }

    public int getBirthYear() {
        return birthYear;  // Returns the user's birth year
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;  // Sets the user's birth year
    }
}
