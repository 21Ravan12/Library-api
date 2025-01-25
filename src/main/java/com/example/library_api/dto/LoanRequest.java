package com.example.library_api.dto;

import java.time.LocalDate;

public class LoanRequest {
    private Long id;  // Unique identifier for the loan request.

    private Long bookId;  // ID of the book being loaned.
    private Long userId;  // ID of the user who is borrowing the book.

    private LocalDate loanDate;  // The date when the book was borrowed.
    private LocalDate dueDate;  // The date when the borrowed book is due for return.
    private LocalDate returnDate;  // The date when the book was actually returned.

    private String sortBy;  // Field by which the loans should be sorted (e.g., loanDate, dueDate).
    private String sortDirection;  // Sorting direction, either "asc" (ascending) or "desc" (descending).

    private Integer count;  // The number of copies of the book being borrowed.

    // Getter and Setter methods for each field
    public Long getId() {
        return id;  // Returns the unique identifier for the loan request.
    }

    public void setId(Long id) {
        this.id = id;  // Sets the unique identifier for the loan request.
    }

    public Long getBookId() {
        return bookId;  // Returns the ID of the book being loaned.
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;  // Sets the ID of the book being loaned.
    }

    public Long getUserId() {
        return userId;  // Returns the ID of the user borrowing the book.
    }

    public void setUserId(Long userId) {
        this.userId = userId;  // Sets the ID of the user borrowing the book.
    }

    public LocalDate getLoanDate() {
        return loanDate;  // Returns the date when the book was borrowed.
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;  // Sets the date when the book was borrowed.
    }

    public LocalDate getDueDate() {
        return dueDate;  // Returns the due date for returning the borrowed book.
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;  // Sets the due date for returning the borrowed book.
    }

    public LocalDate getReturnDate() {
        return returnDate;  // Returns the date when the book was actually returned.
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;  // Sets the date when the book was actually returned.
    }

    public Integer getCount() {
        return count;  // Returns the number of copies of the book being borrowed.
    }

    public void setCount(Integer count) {
        this.count = count;  // Sets the number of copies of the book being borrowed.
    }

    public String getSortBy() {
        return sortBy;  // Returns the field by which the loan requests should be sorted.
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;  // Sets the field by which the loan requests should be sorted.
    }

    public String getSortDirection() {
        return sortDirection;  // Returns the sorting direction, either "asc" or "desc".
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;  // Sets the sorting direction, either "asc" or "desc".
    }
}
