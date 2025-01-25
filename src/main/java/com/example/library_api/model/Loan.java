package com.example.library_api.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the loan record.

    @ManyToOne  // Defines a many-to-one relationship with the Book entity.
    @JoinColumn(name = "book_id", nullable = false)  // Foreign key column linking to the Book entity.
    private Book book;  // The book being borrowed in this loan.

    @ManyToOne  // Defines a many-to-one relationship with the User entity.
    @JoinColumn(name = "user_id", nullable = false)  // Foreign key column linking to the User entity.
    private User user;  // The user who borrowed the book.

    private LocalDate loanDate;  // The date when the book was borrowed.
    private LocalDate dueDate;   // The date when the book is due to be returned.
    private LocalDate returnDate;  // The date when the book was actually returned.

    private Integer count;  // The number of copies of the book borrowed in this loan.

    // Getters and Setters

    public Long getId() {
        return id;  // Returns the unique identifier for the loan.
    }

    public void setId(Long id) {
        this.id = id;  // Sets the unique identifier for the loan.
    }

    public Book getBook() {
        return book;  // Returns the book associated with the loan.
    }

    public void setBook(Book book) {
        this.book = book;  // Sets the book associated with the loan.
    }

    public User getUser() {
        return user;  // Returns the user who borrowed the book.
    }

    public void setUser(User user) {
        this.user = user;  // Sets the user who borrowed the book.
    }

    public LocalDate getLoanDate() {
        return loanDate;  // Returns the date the book was borrowed.
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;  // Sets the date the book was borrowed.
    }

    public LocalDate getDueDate() {
        return dueDate;  // Returns the due date for the loan.
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;  // Sets the due date for the loan.
    }

    public LocalDate getReturnDate() {
        return returnDate;  // Returns the date when the book was returned.
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;  // Sets the date when the book was returned.
    }

    public Integer getCount() {
        return count;  // Returns the number of copies of the book borrowed.
    }

    public void setCount(Integer count) {
        this.count = count;  // Sets the number of copies of the book borrowed.
    }
}
