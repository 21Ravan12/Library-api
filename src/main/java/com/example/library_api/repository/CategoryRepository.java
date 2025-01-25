package com.example.library_api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library_api.model.Category;

// This interface extends JpaRepository to perform CRUD operations for the Category entity
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Method to find categories by name (case-insensitive) and sort the result
    List<Category> findByNameContainingIgnoreCase(String name, Sort sort);

    // Method to find categories by their start date
    List<Category> findByStartDate(Date startDate);

    // Method to find categories by description (case-insensitive) and sort the result
    List<Category> findByDescriptionContainingIgnoreCase(String description, Sort sort);

    // Method to find all categories and sort them in ascending order by name
    List<Category> findAllByOrderByNameAsc();

    // Method to find all categories and sort them in descending order by name
    List<Category> findAllByOrderByNameDesc();
}
