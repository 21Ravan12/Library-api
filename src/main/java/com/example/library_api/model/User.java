package com.example.library_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Specifies that the "users" table will be created in the database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the ID will be generated automatically with incremental values
    private Long id;  // Unique identifier for the user

    @Column(nullable = false, unique = true) // Specifies that the email column cannot be null and must be unique
    private String email;  // User's email address

    @Column(nullable = false) // Specifies that the password column cannot be null
    private String password;  // User's password

    @Column(nullable = false) // Specifies that the name column cannot be null
    private String name;  // User's name

    @Column(nullable = false) // Specifies that the birth year column cannot be null
    private int birthyear;  // User's birth year
    
    @Column(nullable = true) // Specifies that the tier column can be null
    private String tier;  // User's membership tier (if any)

    // Getters and Setters

    public Long getId() {
        return id;  // Returns the unique identifier for the user
    }

    public void setId(Long id) {
        this.id = id;  // Sets the unique identifier for the user
    }

    public String getEmail() {
        return email;  // Returns the user's email address
    }

    public void setEmail(String email) {
        this.email = email;  // Sets the user's email address
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

    public int getBirthyear() {
        return birthyear;  // Returns the user's birth year
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;  // Sets the user's birth year
    }

    public void setTier(String tier) {
        this.tier = tier;  // Sets the user's membership tier (if any)
    }

    public String getTier() {
        return tier;  // Returns the user's membership tier (if any)
    }

}
