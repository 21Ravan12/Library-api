package com.example.library_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_api.dto.BookRequest;
import com.example.library_api.model.Book;
import com.example.library_api.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService; // The BookService is injected here to handle book-related logic.

    // Method to add a new book to the library
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest) {
        try {
            // Validation checks for the required fields
            if (bookRequest.getTitle() == null || bookRequest.getTitle().isBlank()) {
                return ResponseEntity.badRequest().body("Error: Book title is required.");
            }
            if (bookRequest.getAuthor() == null || bookRequest.getAuthor().isBlank()) {
                return ResponseEntity.badRequest().body("Error: Author name is required.");
            }
            if (bookRequest.getPublicationYear() <= 0) {
                return ResponseEntity.badRequest().body("Error: Valid publication year is required.");
            }
            if (bookRequest.getGenre() == null || bookRequest.getGenre().isBlank()) {
                return ResponseEntity.badRequest().body("Error: Genre is required.");
            }
            if (bookRequest.getPrice() == null || bookRequest.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                return ResponseEntity.badRequest().body("Error: Price must be a positive value.");
            }
            if (bookRequest.getStatus() == null || bookRequest.getStatus().isBlank()) {
                return ResponseEntity.badRequest().body("Error: Book status is required.");
            }
            if (bookRequest.getCount() == null || bookRequest.getCount() < 0) {
                return ResponseEntity.badRequest().body("Error: Count must be a non-negative value.");
            }
    
            // Add the new book to the library through the BookService
            Book addedBook = bookService.addBook(bookRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    
        } catch (IllegalArgumentException e) {
            // Catch validation errors and return them
            return ResponseEntity.badRequest().body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while adding the book.");
        }
    }
    
    // Method to search for books based on given criteria
    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestBody BookRequest bookRequest) {
        // Extract parameters from BookRequest and set them to null if not provided
        String title = bookRequest.getTitle() != null ? bookRequest.getTitle() : null;
        String author = bookRequest.getAuthor() != null ? bookRequest.getAuthor() : null;
        Integer publicationYear = bookRequest.getPublicationYear() > 0 ? bookRequest.getPublicationYear() : null;
        String genre = bookRequest.getGenre() != null ? bookRequest.getGenre() : null;
        BigDecimal price = bookRequest.getPrice() != null ? bookRequest.getPrice() : null;
        String status = bookRequest.getStatus() != null ? bookRequest.getStatus() : null;
        String sortBy = bookRequest.getSortBy() != null ? bookRequest.getSortBy() : null;
        String sortDirection = bookRequest.getSortDirection() != null ? bookRequest.getSortDirection() : null;

        try {
            // Perform the book search using BookService
            List<Book> books = bookService.searchBooks(title, author, publicationYear, genre, price, status, sortBy, sortDirection);
            
            // If no books are found, return a not found response
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found matching the criteria.");
            }
            // Return the list of books
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            // Handle any errors that occur during the search
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while searching for books.");
        }
    }

    // Method to update an existing book in the library
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody BookRequest bookRequest) {
        try {
            // Validation check for the book ID
            if (bookRequest.getId() < 0) {
                return ResponseEntity.badRequest().body("Error: Book id is required.");
            }
    
            // Update the book details through BookService
            Book updatedBook = bookService.updateBook(bookRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedBook);
    
        } catch (IllegalArgumentException e) {
            // Catch validation errors
            return ResponseEntity.badRequest().body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while updating the book.");
        }
    }
    
    // Method to delete a book from the library
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBook(@RequestBody BookRequest bookRequest) {
        try {
            // Extract book ID from the request body
            Long bookId = bookRequest.getId();
            
            // Delete the book using BookService
            bookService.deleteBook(bookId);
            
            // If deletion is successful, return a confirmation message
            return ResponseEntity.ok("Book with ID " + bookId + " has been successfully deleted.");
        } catch (IllegalArgumentException e) {
            // Catch validation errors
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while deleting the book.");
        }
    }
}
