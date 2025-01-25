package com.example.library_api.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "categories")  // This annotation indicates that the class is a database entity and maps to the "categories" table.
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Unique identifier for the category.

    @Column(nullable = false, unique = true)
    private String name;  // The name of the category (e.g., Fiction, Non-fiction).

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;  // The date when the category was created or started.

    @Column(nullable = false)
    private String description;  // A brief description of the category.

    // Getter and Setter methods for each field

    public int getId() {
        return id;  // Returns the unique identifier for the category.
    }

    public void setId(int id) {
        this.id = id;  // Sets the unique identifier for the category.
    }

    public String getName() {
        return name;  // Returns the name of the category.
    }

    public void setName(String name) {
        this.name = name;  // Sets the name of the category.
    }

    public Date getStartDate() {
        return startDate;  // Returns the start date of the category.
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;  // Sets the start date of the category.
    }

    public String getDescription() {
        return description;  // Returns the description of the category.
    }

    public void setDescription(String description) {
        this.description = description;  // Sets the description of the category.
    }

}
