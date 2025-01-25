package com.example.library_api.dto;

public class UserRequest {
    private String email;  // The user's email address.
    private String password;  // The user's password.
    private String name;  // The user's name.
    private String tier;  // The user's membership tier (e.g., regular, premium).
    private int birthyear;  // The user's birth year.

    // Getter and Setter methods for each field

    public String getEmail() {
        return email;  // Returns the user's email address.
    }

    public void setEmail(String email) {
        this.email = email;  // Sets the user's email address.
    }

    public String getPassword() {
        return password;  // Returns the user's password.
    }

    public void setPassword(String password) {
        this.password = password;  // Sets the user's password.
    }

    public String getName() {
        return name;  // Returns the user's name.
    }

    public void setName(String name) {
        this.name = name;  // Sets the user's name.
    }

    public int getBirthyear() {
        return birthyear;  // Returns the user's birth year.
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;  // Sets the user's birth year.
    }

    public String getTier() {
        return tier;  // Returns the user's membership tier.
    }

    public void setTier(String tier) {
        this.tier = tier;  // Sets the user's membership tier.
    }
}
