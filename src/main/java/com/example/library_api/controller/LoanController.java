package com.example.library_api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_api.dto.LoanRequest;
import com.example.library_api.model.Loan;
import com.example.library_api.service.LoanService;

@RestController
@RequestMapping("/api/loans") // Define the base URL path for the loan-related endpoints
public class LoanController {
    private final LoanService loanService; // Injecting LoanService to handle the business logic

    public LoanController(LoanService loanService) {
        this.loanService = loanService; // Constructor to initialize the LoanService
    }

    @PostMapping("/borrow") // Endpoint for borrowing a book
    public ResponseEntity<String> borrowBook(@RequestBody LoanRequest loanRequest) {
        Long bookId = loanRequest.getBookId(); // Extract book ID from the request
        Long userId = loanRequest.getUserId(); // Extract user ID from the request
        Integer count = loanRequest.getCount(); // Extract count of books from the request
        String response = loanService.borrowBook(bookId, userId, count); // Call service to borrow the book
        return ResponseEntity.ok(response); // Return the response message
    }

    @PostMapping("/return") // Endpoint for returning a book
    public ResponseEntity<String> returnBook(@RequestBody LoanRequest loanRequest) {
        Long bookId = loanRequest.getBookId(); // Extract book ID from the request
        String response = loanService.returnBook(bookId); // Call service to return the book
        return ResponseEntity.ok(response); // Return the response message
    }
    
    @GetMapping("/search") // Endpoint for searching loans based on criteria
    public ResponseEntity<?> searchLoans(@RequestBody LoanRequest loanRequest) {
        // Extract search parameters from the LoanRequest object with null checks
        Long bookId = loanRequest.getBookId() != null ? loanRequest.getBookId() : null;
        Long userId = loanRequest.getUserId() != null ? loanRequest.getUserId() : null;
        LocalDate loanDate = loanRequest.getLoanDate() != null ? loanRequest.getLoanDate() : null;
        LocalDate dueDate = loanRequest.getDueDate() != null ? loanRequest.getDueDate() : null;
        LocalDate returnDate = loanRequest.getReturnDate() != null ? loanRequest.getReturnDate() : null;
        String sortBy = loanRequest.getSortBy() != null ? loanRequest.getSortBy() : "loanDate"; // Default sort by loan date
        String sortDirection = loanRequest.getSortDirection() != null ? loanRequest.getSortDirection() : "asc"; // Default ascending order

        try {
            // Perform the loan search with the provided criteria
            List<Loan> searchLoans = loanService.searchLoans(
                bookId, userId, loanDate, dueDate, returnDate, sortBy, sortDirection
            );

            // If no loans are found, return a 404 response with a message
            if (searchLoans.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("No loans found matching the criteria.");
            }

            // Return the found loans with a 200 OK response
            return ResponseEntity.ok(searchLoans);
        } catch (Exception e) {
            // Handle any unexpected errors and return a 500 response with an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while searching for loans.");
        }
    }

    @PutMapping("/update") // Endpoint for updating an existing loan
    public ResponseEntity<Loan> updateLoan(@RequestBody LoanRequest loanRequest) {
        try {
            Long loanId = loanRequest.getId(); // Extract loan ID from the request
            Loan updatedLoan = loanService.updateLoan(loanId, loanRequest); // Call service to update the loan
            return ResponseEntity.ok(updatedLoan); // Return the updated loan with a 200 OK response
        } catch (Exception ex) {
            // Handle any errors during the update process and return a 500 response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
