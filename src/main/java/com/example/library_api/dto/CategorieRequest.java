package com.example.library_api.dto;

import java.util.Date;

public class CategorieRequest {
    
    private int id;  // The unique identifier for the category.
    private String name;  // The name of the category.
    private Date startDate;  // The date when the category was created or became available.
    private String description;  // A description of the category, providing more information.
    private String sortBy;  // The field by which to sort the list of categories (e.g., name, startDate).
    private String sortDirection;  // The direction of sorting, either "asc" (ascending) or "desc" (descending).

    // Getter and Setter methods for each field
    public int getId() {
        return id;  // Returns the unique identifier of the category.
    }

    public void setId(int id) {
        this.id = id;  // Sets the unique identifier of the category.
    }

    public Date getStartDate() {
        return startDate;  // Returns the start date of the category.
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;  // Sets the start date of the category.
    }

    public String getName() {
        return name;  // Returns the name of the category.
    }

    public void setName(String name) {
        this.name = name;  // Sets the name of the category.
    }

    public String getSortBy() {
        return sortBy;  // Returns the field by which to sort the categories.
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;  // Sets the field by which to sort the categories.
    }

    public String getSortDirection() {
        return sortDirection;  // Returns the direction of sorting (asc or desc).
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;  // Sets the direction of sorting (asc or desc).
    }

    public String getDescription() {
        return description;  // Returns the description of the category.
    }

    public void setDescription(String description) {
        this.description = description;  // Sets the description of the category.
    }
}
