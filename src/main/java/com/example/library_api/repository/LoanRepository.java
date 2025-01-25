package com.example.library_api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library_api.model.Loan;

// This interface extends JpaRepository to perform CRUD operations for the Loan entity
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Method to find a loan for a specific book where the return date is null (i.e., not yet returned)
    Optional<Loan> findByBookIdAndReturnDateIsNull(Long bookId);

    // Method to find all loans for a specific book, sorted by the given criteria
    List<Loan> findByBookId(Long bookId, Sort sort);

    // Method to find all loans for a specific user, sorted by the given criteria
    List<Loan> findByUserId(Long userId, Sort sort);

    // Method to find loans made within a specific date range, sorted by the given criteria
    List<Loan> findByLoanDateBetween(LocalDate startDate, LocalDate endDate, Sort sort);

    // Method to find loans that were returned on a specific date, sorted by the given criteria
    List<Loan> findByReturnDate(LocalDate returnDate, Sort sort);
}
