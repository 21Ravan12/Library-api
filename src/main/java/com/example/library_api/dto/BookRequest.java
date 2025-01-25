package com.example.library_api.dto;

import java.math.BigDecimal;

public class BookRequest {
    private String title;  // The title of the book.
    private String author;  // The author of the book.
    private int publicationYear;  // The year the book was published.
    private String genre;  // The genre or category of the book (e.g., fiction, science).
    private BigDecimal price;  // The price of the book, represented as a BigDecimal for precision.
    private String status;  // The status of the book (e.g., available, borrowed).
    private Integer count;  // The total number of copies of the book available.
    private String sortBy;  // The field by which to sort the list of books (e.g., title, author).
    private String sortDirection;  // The direction of sorting, either "asc" (ascending) or "desc" (descending).
    private int reservationCount;  // The number of times the book has been reserved.
    private int borrowCount;  // The number of times the book has been borrowed.
    private long id;  // The unique identifier of the book.

    // Getter and Setter methods for each field
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
        return publicationYear;  // Returns the publication year of the book.
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;  // Sets the publication year of the book.
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
        return status;  // Returns the status of the book.
    }
    
    public void setStatus(String status) {
        this.status = status;  // Sets the status of the book.
    }
    
    public Integer getCount() {
        return count;  // Returns the count of available copies of the book.
    }
    
    public void setCount(Integer count) {
        this.count = count;  // Sets the count of available copies of the book.
    }
   
    public String getSortBy() {
        return sortBy;  // Returns the field by which to sort the books.
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;  // Sets the field by which to sort the books.
    }

    public String getSortDirection() {
        return sortDirection;  // Returns the direction of sorting (asc or desc).
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;  // Sets the direction of sorting (asc or desc).
    }

    public long getId() {
        return id;  // Returns the unique identifier of the book.
    }

    public void setId(Long id) {
        this.id = id;  // Sets the unique identifier of the book.
    }
    
    public Integer getReservationCount() {
        return reservationCount;  // Returns the number of times the book has been reserved.
    }
    
    public void setReservationCount(Integer reservationCount) {
        this.reservationCount = reservationCount;  // Sets the number of times the book has been reserved.
    }

    public Integer getBorrowCount() {
        return borrowCount;  // Returns the number of times the book has been borrowed.
    }
    
    public void setBorrowCount(Integer borrowCount) {
        this.borrowCount = borrowCount;  // Sets the number of times the book has been borrowed.
    }
}
