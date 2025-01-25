package com.example.library_api.dto;

import java.util.Date;

public class ReservationRequest {
    
    private int id;  // The unique identifier for the reservation.
    private int bookId;  // The ID of the book being reserved.
    private int userId;  // The ID of the user making the reservation.
    private int count;  // The number of copies of the book being reserved.
    private Date startDate;  // The start date of the reservation period.
    private Date endDate;  // The end date of the reservation period.
    private String sortBy;  // The field by which the results should be sorted.
    private String sortDirection;  // The direction (ascending or descending) in which the results should be sorted.

    // Getter and Setter methods for each field

    public int getBookId() {
        return bookId;  // Returns the ID of the book being reserved.
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;  // Sets the ID of the book being reserved.
    }

    public int getId() {
        return id;  // Returns the unique identifier for the reservation.
    }

    public void setId(int id) {
        this.id = id;  // Sets the unique identifier for the reservation.
    }

    public int getUserId() {
        return userId;  // Returns the ID of the user making the reservation.
    }

    public void setUserId(int userId) {
        this.userId = userId;  // Sets the ID of the user making the reservation.
    }

    public Date getStartDate() {
        return startDate;  // Returns the start date of the reservation period.
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;  // Sets the start date of the reservation period.
    }

    public Date getEndDate() {
        return endDate;  // Returns the end date of the reservation period.
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;  // Sets the end date of the reservation period.
    }

    public int getCount() {
        return count;  // Returns the number of copies of the book being reserved.
    }

    public void setCount(int count) {
        this.count = count;  // Sets the number of copies of the book being reserved.
    }

    public String getSortBy() {
        return sortBy;  // Returns the field by which the results should be sorted.
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;  // Sets the field by which the results should be sorted.
    }

    public String getSortDirection() {
        return sortDirection;  // Returns the direction in which the results should be sorted.
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;  // Sets the direction in which the results should be sorted.
    }
}
