package com.example.library_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library_api.dto.BookRequest;
import com.example.library_api.model.Book;
import com.example.library_api.model.Category;
import com.example.library_api.repository.BookRepository;
import com.example.library_api.repository.CategoryRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;  // Repository for interacting with the Book entity in the database
    @Autowired
    private CategoryRepository categoryRepository;  // Repository for interacting with the Category entity in the database

    // Adds a new book to the database
    public Book addBook(BookRequest bookRequest) {
        try {
            // Retrieve all category names to validate the genre of the book
            List<String> categoryNames = categoryRepository.findAll().stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());
            
            // Debug: Log the available category names
            System.out.println("Available categories: " + categoryNames);
    
            // Validate the genre of the book against the available categories
            if (!categoryNames.contains(bookRequest.getGenre())) {
                // Debug: Log the invalid genre
                System.out.println("Invalid genre provided: " + bookRequest.getGenre());
                throw new IllegalArgumentException("Error: Genre '" + bookRequest.getGenre() + "' is not a valid category.");
            }
    
            // Debug: Log valid genre
            System.out.println("Genre is valid: " + bookRequest.getGenre());
    
            // Create a new Book object with the data from the request
            Book book = new Book();
            book.setTitle(bookRequest.getTitle());
            book.setAuthor(bookRequest.getAuthor());
            book.setPublicationYear(bookRequest.getPublicationYear());
            book.setGenre(bookRequest.getGenre());
            book.setPrice(bookRequest.getPrice());
            book.setCount(bookRequest.getCount());
            book.setStatus(bookRequest.getStatus());
    
            // Debug: Log the created Book object
            System.out.println("Book object created: " + book);
    
            // Save the Book to the database
            Book savedBook = bookRepository.save(book);
            
            // Debug: Log the saved Book object
            System.out.println("Book saved to database: " + savedBook);
    
            return savedBook;
        } catch (IllegalArgumentException e) {
            // Log validation error
            System.err.println("Validation error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            // Log unexpected error
            System.err.println("Unexpected error occurred while adding book: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while adding the book.");
        }
    }

    // Searches for books based on various parameters and sorting options
    public List<Book> searchBooks(String title, String author, Integer publicationYear, String genre, 
    BigDecimal price, String status, String sortBy, String sortDirection) {

        Sort sort = Sort.unsorted(); // Default sorting is unsorted

        // Determine sorting order based on user input
        if (sortBy != null && sortDirection != null) {
            if (sortDirection.equalsIgnoreCase("asc")) {
                sort = Sort.by(Sort.Direction.ASC, sortBy); // Ascending order
            } else if (sortDirection.equalsIgnoreCase("desc")) {
                sort = Sort.by(Sort.Direction.DESC, sortBy); // Descending order
            }
        }

        // Search based on the provided parameters
        if (title != null) {
            return bookRepository.findByTitleContainingIgnoreCase(title, sort);
        } else if (author != null) {
            return bookRepository.findByAuthorIgnoreCase(author, sort);
        } else if (publicationYear != null) {
            return bookRepository.findByPublicationYear(publicationYear, sort);
        } else if (genre != null) {
            return bookRepository.findByGenreIgnoreCase(genre, sort);
        } else if (price != null) {
            return bookRepository.findByPriceLessThanEqual(price, sort);
        } else if (status != null) {
            return bookRepository.findByStatusIgnoreCase(status, sort);
        } else {
            return bookRepository.findAll(sort); // Return all books with the specified sorting
        }
    }

    // Updates an existing book based on the data in the request
    public Book updateBook(BookRequest bookRequest) {
        // Get the ID of the book to update
        Long bookId = bookRequest.getId(); 

        // If the genre is provided, validate it
        if (bookRequest.getGenre() != null) {
            List<String> categoryNames = categoryRepository.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

            // Debug: Log the available categories
            System.out.println("Available categories: " + categoryNames);

            // Validate the genre
            if (!categoryNames.contains(bookRequest.getGenre())) {
                // Debug: Log invalid genre
                System.out.println("Invalid genre provided: " + bookRequest.getGenre());
                throw new IllegalArgumentException("Error: Genre '" + bookRequest.getGenre() + "' is not a valid category.");
            }
        }

        // Find the book by its ID, if it exists
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        // Update the book properties with the new values
        if (bookRequest.getTitle() != null) book.setTitle(bookRequest.getTitle());
        if (bookRequest.getAuthor() != null) book.setAuthor(bookRequest.getAuthor());
        if (bookRequest.getPublicationYear() != 0) book.setPublicationYear(bookRequest.getPublicationYear());
        if (bookRequest.getGenre() != null) book.setGenre(bookRequest.getGenre());
        if (bookRequest.getPrice() != null) book.setPrice(bookRequest.getPrice());
        if (bookRequest.getCount() != null) book.setCount(bookRequest.getCount());
        if (bookRequest.getStatus() != null) book.setStatus(bookRequest.getStatus());
        if (bookRequest.getBorrowCount() != null) book.setBorrowCount(bookRequest.getBorrowCount());
        if (bookRequest.getReservationCount() != null) book.setReservationCount(bookRequest.getReservationCount());

        // Save the updated book to the database
        return bookRepository.save(book);
    }

    // Deletes a book by its ID
    public void deleteBook(Long id) {
        // Check if the book exists before trying to delete it
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Book with ID " + id + " not found.");
        }
    }
}
