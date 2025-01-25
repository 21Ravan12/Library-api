package com.example.library_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the book.
    
    private String title;  // The title of the book.
    
    private String author;  // The author of the book.
    
    private int publicationYear;  // The year the book was published.
    
    private String genre;  // The genre or category of the book (e.g., Fiction, Non-fiction, etc.).
    
    private BigDecimal price;  // The price of the book.
    
    private String status;  // The current status of the book (e.g., Available, Borrowed, Reserved).
    
    private Integer count;  // The total number of copies of the book available.
    
    private int reservationCount;  // The number of reservations for this book.
    
    private int borrowCount;  // The number of times this book has been borrowed.

    // Getter and Setter methods for each field

    public Long getId() {
        return id;  // Returns the unique identifier for the book.
    }

    public void setId(Long id) {
        this.id = id;  // Sets the unique identifier for the book.
    }

    public String getTitle() {
        return title;  // Returns the title of the book.
    }

    public void setTitle(String title) {
        this.title = title;  // Sets the title of the book.
    }

    public String getAuthor() {
        return author;  // Returns the author of the book.
    }

    public void setAuthor(String author) {
        this.author = author;  // Sets the author of the book.
    }

    public int getPublicationYear() {
        return publicationYear;  // Returns the year the book was published.
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;  // Sets the year the book was published.
    }

    public String getGenre() {
        return genre;  // Returns the genre of the book.
    }

    public void setGenre(String genre) {
        this.genre = genre;  // Sets the genre of the book.
    }

    public BigDecimal getPrice() {
        return price;  // Returns the price of the book.
    }

    public void setPrice(BigDecimal price) {
        this.price = price;  // Sets the price of the book.
    }

    public String getStatus() {
        return status;  // Returns the current status of the book.
    }
    
    public void setStatus(String status) {
        this.status = status;  // Sets the current status of the book.
    }

    public Integer getCount() {
        return count;  // Returns the total number of available copies of the book.
    }
    
    public void setCount(Integer count) {
        this.count = count;  // Sets the total number of available copies of the book.
    }

    public Integer getReservationCount() {
        return reservationCount;  // Returns the number of reservations for this book.
    }
    
    public void setReservationCount(Integer reservationCount) {
        this.reservationCount = reservationCount;  // Sets the number of reservations for this book.
    }

    public Integer getBorrowCount() {
        return borrowCount;  // Returns the number of times the book has been borrowed.
    }
    
    public void setBorrowCount(Integer borrowCount) {
        this.borrowCount = borrowCount;  // Sets the number of times the book has been borrowed.
    }
}
