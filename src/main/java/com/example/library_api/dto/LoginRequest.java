package com.example.library_api.dto;

public class LoginRequest {
    private String email;  // The email address of the user attempting to log in.
    private String password;  // The password associated with the user's email.

    // Getter and Setter methods for each field
    public String getEmail() {
        return email;  // Returns the email address of the user.
    }

    public void setEmail(String email) {
        this.email = email;  // Sets the email address of the user.
    }

    public String getPassword() {
        return password;  // Returns the password associated with the user's email.
    }

    public void setPassword(String password) {
        this.password = password;  // Sets the password for the user.
    }
}
