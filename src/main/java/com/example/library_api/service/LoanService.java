package com.example.library_api.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library_api.dto.LoanRequest;
import com.example.library_api.model.Book;
import com.example.library_api.model.Loan;
import com.example.library_api.model.User;
import com.example.library_api.repository.BookRepository;
import com.example.library_api.repository.LoanRepository;
import com.example.library_api.repository.UserRepository;

import ch.qos.logback.classic.Logger;

@Service
public class LoanService {
    // Repositories for interacting with the database
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    // Constructor to initialize the repositories
    public LoanService(BookRepository bookRepository, LoanRepository loanRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    // Method to borrow a book, given the bookId, userId, and the count of books
    public String borrowBook(Long bookId, Long userId, Integer count) {
        // Check if the book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if the book is available for borrowing
        if (book.getCount() <= 0 || book.getCount() <= count ) {
            return "Book is not available for reservation!";
        }

        // Check if the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Borrow the book by reducing its available count and increasing the borrow count
        book.setCount(book.getCount() - count); 
        book.setBorrowCount(book.getBorrowCount() + count);
        bookRepository.save(book);

        // Create a new loan record and save it
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setCount(count);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14)); // Set the due date (14 days later)
        loanRepository.save(loan);

        return "Book borrowed successfully.";
    }

    // Method to return a borrowed book
    public String returnBook(Long bookId) {
        // Find the active loan record for the book
        Loan loan = loanRepository.findByBookIdAndReturnDateIsNull(bookId)
                .orElseThrow(() -> new RuntimeException("No active loan record found for this book"));

        // Mark the book as returned
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        // Update the book count and borrowed count
        Book book = loan.getBook();
        book.setCount(book.getCount() + loan.getCount());
        book.setBorrowCount(book.getBorrowCount() - loan.getCount());  
        bookRepository.save(book);

        return "Book returned successfully.";
    }

    // Method to search for loans based on various filters and sorting options
    public List<Loan> searchLoans(Long bookId, Long userId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, String sortBy, String sortDirection) {

        Sort sort = Sort.unsorted(); // Default is no sorting
    
        // Check for sorting parameters and apply them if valid
        if (sortBy != null && sortDirection != null) {
            if (sortDirection.equalsIgnoreCase("asc")) {
                sort = Sort.by(Sort.Direction.ASC, sortBy); // Ascending sort
            } else if (sortDirection.equalsIgnoreCase("desc")) {
                sort = Sort.by(Sort.Direction.DESC, sortBy); // Descending sort
            }
        }
    
        // Apply filtering logic based on provided parameters
        if (bookId != null) {
            return loanRepository.findByBookId(bookId, sort);
        } else if (userId != null) {
            return loanRepository.findByUserId(userId, sort);
        } else if (loanDate != null && dueDate != null) {
            return loanRepository.findByLoanDateBetween(loanDate, dueDate, sort);
        } else if (returnDate != null) {
            return loanRepository.findByReturnDate(returnDate, sort);
        } else {
            return loanRepository.findAll(sort); // Return all loans if no filter is applied
        }
    }

    // Method to update a loan's details
    public Loan updateLoan(Long loanId, LoanRequest loanRequest) {
        // Logger initialization
        Logger logger = (Logger) LoggerFactory.getLogger(getClass());
        
        logger.debug("Updating loan with id: {}", loanId);
    
        // Check for existing loan record
        Loan existingLoan = loanRepository.findById(loanId)
            .orElseThrow(() -> {
                logger.error("Loan not found with id: {}", loanId);
                return new RuntimeException("Loan not found with id: " + loanId);
            });
    
        // Update the loan with the provided values
        if (loanRequest.getBookId() != null) {
            logger.debug("Checking for book with id: {}", loanRequest.getBookId());
    
            // Check if the book exists and set it
            Book book = bookRepository.findById(loanRequest.getBookId())
                .orElseThrow(() -> {
                    logger.error("Book not found with id: {}", loanRequest.getBookId());
                    return new RuntimeException("Book not found with id: " + loanRequest.getBookId());
                });
    
            existingLoan.setBook(book);
            logger.debug("Book set for loan with id: {}", loanId);
        }
    
        if (loanRequest.getUserId() != null) {
            logger.debug("Checking for user with id: {}", loanRequest.getUserId());
    
            // Check if the user exists and set it
            User user = userRepository.findById(loanRequest.getUserId())
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", loanRequest.getUserId());
                    return new RuntimeException("User not found with id: " + loanRequest.getUserId());
                });
    
            existingLoan.setUser(user);
            logger.debug("User set for loan with id: {}", loanId);
        }
    
        if (loanRequest.getLoanDate() != null) {
            logger.debug("Setting loan date for loan with id: {}", loanId);
            existingLoan.setLoanDate(loanRequest.getLoanDate());
        }
    
        if (loanRequest.getDueDate() != null) {
            logger.debug("Setting due date for loan with id: {}", loanId);
            existingLoan.setDueDate(loanRequest.getDueDate());
        }
    
        if (loanRequest.getReturnDate() != null) {
            logger.debug("Setting return date for loan with id: {}", loanId);
            existingLoan.setReturnDate(loanRequest.getReturnDate());
        }
    
        // Save the updated loan record and return it
        logger.debug("Saving updated loan with id: {}", loanId);
        return loanRepository.save(existingLoan);
    }
    
}
