package com.example.library_api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library_api.dto.CategorieRequest;
import com.example.library_api.model.Category;
import com.example.library_api.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to add a new category
    public Category addCategory(CategorieRequest categorieRequest) {
        // Creating a new category object
        Category category = new Category();
        category.setName(categorieRequest.getName()); // Set the name of the category
        category.setStartDate(categorieRequest.getStartDate()); // Set the start date of the category
        category.setDescription(categorieRequest.getDescription()); // Set the description of the category

        // Save the new category to the database
        return categoryRepository.save(category); 
    }

    // Method to delete an existing category by its ID
    public void deleteCategory(int id) {
        // Find the category by its ID
        Category category = categoryRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Category with ID " + id + " not found")
        );

        // Delete the category from the database
        categoryRepository.delete(category);
    }

    // Method to search for categories based on various parameters
    public List<Category> searchCategories(
    Integer id, 
    String name, 
    String description, 
    Date startDate, 
    String sortBy, 
    String sortDirection) {

        // Default sorting is unsorted
        Sort sort = Sort.unsorted();

        // If sorting parameters are provided, apply them
        if (sortBy != null && sortDirection != null) {
            if (sortDirection.equalsIgnoreCase("asc")) {
                sort = Sort.by(Sort.Direction.ASC, sortBy); // Sort in ascending order
            } else if (sortDirection.equalsIgnoreCase("desc")) {
                sort = Sort.by(Sort.Direction.DESC, sortBy); // Sort in descending order
            }
        }

        // Dynamic query creation based on search parameters
        if (id != 0) {
            // Search by category ID if provided
            return categoryRepository.findById(id)
                    .map(List::of) // Convert single result to list
                    .orElse(List.of()); // Return an empty list if category is not found
        } else if (name != null) {
            // Search by category name if provided
            return categoryRepository.findByNameContainingIgnoreCase(name, sort);
        } else if (description != null) {
            // Search by category description if provided (repository should support this query)
            return categoryRepository.findByDescriptionContainingIgnoreCase(description, sort);
        } else if (startDate != null) {
            // Search by category start date if provided (repository should support this query)
            return categoryRepository.findByStartDate(startDate);
        } else {
            // If no search criteria is given, return all categories with the specified sorting
            return categoryRepository.findAll(sort);
        }
    }

    // Method to update an existing category
    public Category updateCategory(CategorieRequest categorieRequest) {
        // Retrieve the category to be updated by its ID
        int categoryId = categorieRequest.getId();

        // Find the category in the database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId));

        // Update the category's properties if provided
        if (categorieRequest.getName() != null && !categorieRequest.getName().isEmpty()) {
            category.setName(categorieRequest.getName()); // Update name if provided
        }

        if (categorieRequest.getDescription() != null && !categorieRequest.getDescription().isEmpty()) {
            category.setDescription(categorieRequest.getDescription()); // Update description if provided
        }

        // Update the start date if provided
        if (categorieRequest.getStartDate() != null) {
            category.setStartDate(categorieRequest.getStartDate());
        }

        // Save the updated category to the database
        return categoryRepository.save(category); 
    }
}
