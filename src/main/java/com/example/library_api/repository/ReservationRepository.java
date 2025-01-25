package com.example.library_api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library_api.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // Filter by User ID
    List<Reservation> findByUserId(int userId);

    // Filter by Book ID
    List<Reservation> findByBookId(int bookId);

    // Filter by reservation date range (start and end date)
    List<Reservation> findByStartDateBetween(Date startDate, Date endDate, Sort sort);

    // Filter by count greater than or equal to the given value
    List<Reservation> findByCountGreaterThanEqual(int count, Sort sort);
}

