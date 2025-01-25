package com.example.library_api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_api.dto.CategorieRequest;
import com.example.library_api.model.Category;
import com.example.library_api.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategoryService categoryService;

    // Endpoint to add a new category
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategorieRequest categorieRequest) {
        try {
            // Attempt to add the category using the service
            Category category = categoryService.addCategory(categorieRequest);
            // Return the created category with a 201 CREATED status if successful
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (Exception e) {
            // In case of an error, return an internal server error with a message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while adding the category.");
        }
    }

    // Endpoint to delete an existing category by its ID
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody CategorieRequest categorieRequest) {
        try {
            // Extract category ID from the request body
            int id = categorieRequest.getId();
            // Attempt to delete the category using the service
            categoryService.deleteCategory(id);
            // Return a success message with 200 OK status
            return ResponseEntity.ok("Category with ID " + id + " has been successfully deleted.");
        } catch (IllegalArgumentException e) {
            // If there's an issue with the category ID, return a bad request status with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // In case of an unexpected error, return a 500 internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while deleting the category.");
        }
    }

    // Endpoint to search for categories based on various criteria
    @GetMapping("/search")
    public ResponseEntity<?> searchCategories(@RequestBody CategorieRequest categorieRequest) {
        try {
            // Extract search parameters from the request body with null checks
            int id = (categorieRequest.getId() > 0) ? categorieRequest.getId() : 0;
            String name = categorieRequest.getName() != null ? categorieRequest.getName() : null;
            String description = categorieRequest.getDescription() != null ? categorieRequest.getDescription() : null;
            Date startDate = categorieRequest.getStartDate() != null ? categorieRequest.getStartDate() : null;
            String sortBy = categorieRequest.getSortBy() != null ? categorieRequest.getSortBy() : null;
            String sortDirection = categorieRequest.getSortDirection() != null ? categorieRequest.getSortDirection() : null;

            // Call the service to search categories with the provided criteria
            List<Category> categories = categoryService.searchCategories(id, name, description, startDate, sortBy, sortDirection);

            // If no categories are found, return a 404 NOT FOUND status with a message
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No categories found matching the criteria.");
            }

            // If categories are found, return them with a 200 OK status
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            // In case of an error, return a 500 INTERNAL SERVER ERROR status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while searching for categories.");
        }
    }

    // Endpoint to update an existing category
    @PostMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategorieRequest categorieRequest) {
        try {
            // Ensure that the category ID is a valid positive number
            if (categorieRequest.getId() <= 0) {
                return ResponseEntity.badRequest().body("Error: Category ID must be a valid positive number.");
            }

            // Attempt to update the category using the service
            Category updatedCategory = categoryService.updateCategory(categorieRequest);
            // Return the updated category with a 200 OK status if successful
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
        } catch (IllegalArgumentException e) {
            // If there are validation issues, return a 400 BAD REQUEST with the error message
            return ResponseEntity.badRequest().body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            // If any other error occurs, return a 500 INTERNAL SERVER ERROR status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while updating the category.");
        }
    }
}
