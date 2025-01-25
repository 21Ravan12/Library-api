package com.example.library_api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library_api.model.Book;

// This interface extends JpaRepository to perform CRUD operations for the Book entity
public interface BookRepository extends JpaRepository<Book, Long> {

    // Method to find books by title (case-insensitive) and sort the result
    List<Book> findByTitleContainingIgnoreCase(String title, Sort sort);

    // Method to find books by author (case-insensitive) and sort the result
    List<Book> findByAuthorIgnoreCase(String author, Sort sort);

    // Method to find books by publication year and sort the result
    List<Book> findByPublicationYear(Integer publicationYear, Sort sort);

    // Method to find books by genre (case-insensitive) and sort the result
    List<Book> findByGenreIgnoreCase(String genre, Sort sort);

    // Method to find books with a price less than or equal to a given amount and sort the result
    List<Book> findByPriceLessThanEqual(BigDecimal price, Sort sort);

    // Method to find books by their status (case-insensitive) and sort the result
    List<Book> findByStatusIgnoreCase(String status, Sort sort);
}
