package com.example.library_api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library_api.dto.BookRequest;
import com.example.library_api.dto.ReservationRequest;
import com.example.library_api.model.Book;
import com.example.library_api.model.Reservation;
import com.example.library_api.model.User;
import com.example.library_api.repository.BookRepository;
import com.example.library_api.repository.ReservationRepository;
import com.example.library_api.repository.UserRepository;

@Service
public class ReservationService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final BookService bookService; // Ensure BookService is injected

   
    public ReservationService(BookRepository bookRepository, UserRepository userRepository, 
                             ReservationRepository reservationRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
    }

    public String reserveBook(int bookId, int userId, Date startDate, Date endDate, int count) {
        // Find book
        Optional<Book> bookOptional = bookRepository.findById((long) bookId);
        if (bookOptional.isEmpty()) {
            return "Book not found!";
        }

        Book book = bookOptional.get();

        // Check if the book is available
        if (book.getCount() <= 0 || book.getCount() <= count ) {
            return "Book is not available for reservation!";
        }

        // Find user
        Optional<User> userOptional = userRepository.findById((long) userId);
        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        // Create and save reservation
        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(userOptional.get());
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setCount(count);
        reservationRepository.save(reservation);

        // Update book count and status
        BookRequest bookRequest = new BookRequest();
        bookRequest.setId(book.getId());
        bookRequest.setCount(book.getCount() - count); // Decrease the count by 1
        bookRequest.setReservationCount(book.getReservationCount() + count); 
        if (book.getCount() - count <= 0) {
            bookRequest.setStatus("Unavailable"); // Example: Update status if out of stock
        }
        bookService.updateBook(bookRequest); // Call the updateBook method

        return "Reservation successful!";
    }

    public List<Reservation> searchReservations(Integer id,Integer bookId, Date startDate, Date endDate, Integer userId, Integer count, String sortBy, String sortDirection) {

    Sort sort = Sort.unsorted(); // Default to no sorting

    // Apply sorting if provided
    if (sortBy != null && sortDirection != null) {
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(Sort.Direction.ASC, sortBy); // Ascending order
        } else if (sortDirection.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC, sortBy); // Descending order
        }
    }

    // Search logic for filtering reservations based on provided parameters
    if (bookId != null) {
        return reservationRepository.findByBookId(bookId);
    } else if (userId != null) {
        return reservationRepository.findByUserId(userId);
    } else if (startDate != null && endDate != null) {
        return reservationRepository.findByStartDateBetween(startDate, endDate, sort);
    } else if (count != null) {
        return reservationRepository.findByCountGreaterThanEqual(count, sort);
    } else {
        return reservationRepository.findAll(sort); // Return all reservations if no filter is applied
    }
}

    public boolean deleteReservation(Integer id) {
    Optional<Reservation> reservationOptional = reservationRepository.findById(id);

    if (reservationOptional.isPresent()) {
        Reservation reservation = reservationOptional.get();
        Book book = reservation.getBook();

        // Update the book count and reservation count
        book.setCount(book.getCount() + reservation.getCount()); // Add the reserved count back
        book.setReservationCount(book.getReservationCount() - reservation.getCount());
        if (book.getCount() > 0) {
            book.setStatus("Available"); // Mark the book as available if it's back in stock
        }
        bookRepository.save(book); // Save the updated book

        // Delete the reservation
        reservationRepository.deleteById(id);
        return true;
    }
    return false;
}

    public Reservation updateReservation(ReservationRequest reservationRequest) {
    // Get the reservation ID to update
    int reservationId = reservationRequest.getId();

    // Fetch the reservation by ID
    Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));

    // Fetch the book associated with the reservation
    Book book = reservation.getBook();
    
    // Check and update the reservation details
    if (reservationRequest.getEndDate() != null) {
        reservation.setEndDate(reservationRequest.getEndDate());
    }

    if (reservationRequest.getCount() > 0) {
        // Calculate the difference between the new count and the old count
        int countDifference = reservationRequest.getCount() - reservation.getCount();

        // Update the reservation count
        reservation.setCount(reservationRequest.getCount());

        // Update the book's count and reservation count accordingly
        book.setCount(book.getCount() - countDifference);
        book.setReservationCount(book.getReservationCount() + countDifference);

        // Save the updated book details
        bookRepository.save(book);
    }

    // Save the updated reservation details
    return reservationRepository.save(reservation);
}

    
}
