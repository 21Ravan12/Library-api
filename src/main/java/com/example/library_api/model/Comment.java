package com.example.library_api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the comment.

    @JsonIgnore // Excludes this field from JSON serialization
    @ManyToOne(fetch = FetchType.LAZY)  // Defines a many-to-one relationship with the Book entity.
    @JoinColumn(name = "book_id")  // Specifies the foreign key column in the database for the book.
    private Book book;  // The book this comment is associated with.

    @JsonIgnore // Excludes this field from JSON serialization
    @ManyToOne(fetch = FetchType.LAZY)  // Defines a many-to-one relationship with the User entity.
    @JoinColumn(name = "user_id")  // Specifies the foreign key column in the database for the user.
    private User user;  // The user who wrote the comment.

    private String content;  // The text content of the comment.

    @Column(name = "created_at")  // Specifies the column name in the database for the creation date.
    private LocalDateTime createdAt;  // The timestamp when the comment was created.

    // Constructor to initialize the createdAt field automatically when a new comment is created
    public Comment() {
        this.createdAt = LocalDateTime.now();  // Sets the creation time to the current date and time when a new comment is created.
    }

    // Getters and Setters for each field

    public Long getId() {
        return id;  // Returns the unique identifier for the comment.
    }

    public void setId(Long id) {
        this.id = id;  // Sets the unique identifier for the comment.
    }

    public Book getBook() {
        return book;  // Returns the book associated with the comment.
    }

    public void setBook(Book book) {
        this.book = book;  // Sets the book associated with the comment.
    }

    public User getUser() {
        return user;  // Returns the user who wrote the comment.
    }

    public void setUser(User user) {
        this.user = user;  // Sets the user who wrote the comment.
    }

    public String getContent() {
        return content;  // Returns the content of the comment.
    }

    public void setContent(String content) {
        this.content = content;  // Sets the content of the comment.
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;  // Returns the creation timestamp of the comment.
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;  // Sets the creation timestamp of the comment.
    }
}
