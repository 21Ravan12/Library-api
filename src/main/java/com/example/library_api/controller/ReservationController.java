package com.example.library_api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_api.dto.ReservationRequest;
import com.example.library_api.model.Reservation;
import com.example.library_api.service.ReservationService;

@RestController
@RequestMapping("/api/reservations") // Define the base URL path for reservation-related endpoints
public class ReservationController {

    private final ReservationService reservationService; // Injecting ReservationService to handle business logic

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService; // Constructor to initialize ReservationService
    }

    @PostMapping("/reserve") // Endpoint for reserving a book
    public ResponseEntity<String> reserveBook(@RequestBody ReservationRequest reservationRequest) {
        // Validate that the count is greater than 0
        if (reservationRequest.getCount() <= 0) {
            return ResponseEntity.badRequest().body("Reservation count must be greater than zero."); // Return error if invalid
        }

        // Proceed with the reservation process
        String result = reservationService.reserveBook(
                reservationRequest.getBookId(),
                reservationRequest.getUserId(),
                reservationRequest.getStartDate(),
                reservationRequest.getEndDate(),
                reservationRequest.getCount()
        );

        // Return a success response with the result of the reservation
        return ResponseEntity.ok(result); 
    }

    @GetMapping("/search") // Endpoint for searching reservations
    public ResponseEntity<?> searchBooks(@RequestBody ReservationRequest reservationRequest) {
        // Extract parameters from the ReservationRequest, applying null checks
        Integer id = reservationRequest.getBookId() > 0 ? reservationRequest.getBookId() : null;
        Date end_date = reservationRequest.getEndDate() != null ? reservationRequest.getEndDate() : null;
        Date start_date = reservationRequest.getStartDate() != null ? reservationRequest.getStartDate() : null;    
        Integer book_id = reservationRequest.getBookId() > 0 ? reservationRequest.getBookId() : null;
        Integer user_id = reservationRequest.getUserId() > 0 ? reservationRequest.getUserId() : null;
        Integer count = reservationRequest.getCount() > 0 ? reservationRequest.getCount() : null;
        String sortBy = reservationRequest.getSortBy() != null ? reservationRequest.getSortBy() : null;
        String sortDirection = reservationRequest.getSortDirection() != null ? reservationRequest.getSortDirection() : null;

        try {
            // Perform the search for reservations with the specified criteria
            List<Reservation> searchReservations = reservationService.searchReservations(
                id, book_id, end_date, start_date, user_id, count, sortBy, sortDirection
            );
        
            // Check if no reservations are found and return an appropriate message
            if (searchReservations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservations found matching the criteria.");
            }

            // Return the found reservations in the response
            return ResponseEntity.ok(searchReservations); 
        } catch (Exception e) {
            // Handle unexpected errors during the search process and return a 500 error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while searching for reservations.");
        }
    }
    
    @PostMapping("/update") // Endpoint for updating an existing reservation
    public ResponseEntity<?> update(@RequestBody ReservationRequest reservationRequest) {
        try {
            // Validate that the reservation ID is valid
            if (reservationRequest.getId() < 0) {
                return ResponseEntity.badRequest().body("Error: Reservation ID must be a valid positive number.");
            }

            // Perform the update operation
            Reservation update = reservationService.updateReservation(reservationRequest);
            return ResponseEntity.status(HttpStatus.OK).body(update); // Return the updated reservation

        } catch (IllegalArgumentException e) {
            // Handle validation errors and return a bad request response
            return ResponseEntity.badRequest().body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors during the update process and return a 500 error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while updating the reservation.");
        }
    }

    @DeleteMapping("/delete") // Endpoint for deleting a reservation
    public ResponseEntity<String> deleteReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            Integer id = reservationRequest.getId(); // Extract reservation ID from request

            // Call the service to delete the reservation based on its ID
            boolean isDeleted = reservationService.deleteReservation(id); 

            // If the reservation is deleted successfully, return a success message
            if (isDeleted) {
                return ResponseEntity.ok("Reservation deleted successfully.");
            } else {
                // If no reservation is found with the given ID, return a not found message
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Reservation with ID " + id + " not found.");
            }
        } catch (Exception e) {
            // Handle errors that may occur during the deletion process and return a 500 error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while deleting the reservation: " + e.getMessage());
        }
    }
}
