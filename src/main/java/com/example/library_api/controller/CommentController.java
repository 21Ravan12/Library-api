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

import com.example.library_api.dto.CommentRequest;
import com.example.library_api.model.Comment;
import com.example.library_api.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Endpoint to add a new comment
    @PostMapping("/add")
    public Comment addComment(@RequestBody CommentRequest commentRequest) {
        // Call the service method to add the comment
        return commentService.addComment(commentRequest);
    }
    
    // Endpoint to delete a comment by its ID
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody CommentRequest commentRequest) {
        try {
            // Get the comment ID from the request
            Long id = (long) commentRequest.getId();
            
            // Delete the comment using the service
            commentService.deleteComment(id);
            
            // Return a success message with 200 OK status
            return ResponseEntity.ok("Comment with ID " + id + " has been successfully deleted.");
        } catch (IllegalArgumentException e) {
            // Return a bad request status if the ID is invalid
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Return a 500 internal server error status for any unexpected issues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while deleting the comment.");
        }
    }

    // Endpoint to search for comments based on various parameters
    @GetMapping("/search")
    public ResponseEntity<?> searchComments(@RequestBody CommentRequest commentRequest) {
        // Extract search parameters from the request
        Integer id = commentRequest.getId() > 0 ? commentRequest.getId() : null;
        String content = commentRequest.getContent() != null ? commentRequest.getContent() : null;
        Date createdAt = commentRequest.getCreateDate() != null && commentRequest.getCreateDate().getTime() > 0 ? commentRequest.getCreateDate() : null;
        Integer bookId = commentRequest.getBookId() > 0 ? commentRequest.getBookId() : null;
        Integer userId = commentRequest.getUserId() > 0 ? commentRequest.getUserId() : null;
        String sortBy = commentRequest.getSortBy() != null ? commentRequest.getSortBy() : null;
        String sortDirection = commentRequest.getSortDirection() != null ? commentRequest.getSortDirection() : null;

        try {
            // Call the service method to search for comments
            List<Comment> comments = commentService.searchComments(id, content, createdAt, bookId, userId, sortBy, sortDirection);

            // If no comments are found, return a 404 NOT FOUND status
            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No comments found matching the criteria.");
            }

            // Return the found comments with a 200 OK status
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            // Return a 500 internal server error status in case of any unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while searching for comments.");
        }
    }

    // Endpoint to update an existing comment
    @PostMapping("/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest commentRequest) {
        try {
            // Validate that the comment ID is a valid positive number
            if (commentRequest.getId() <= 0) {
                return ResponseEntity.badRequest().body("Error: Comment ID must be a valid positive number.");
            }

            // Call the service method to update the comment
            Comment updatedComment = commentService.updateComment(commentRequest);

            // Return the updated comment with a 200 OK status
            return ResponseEntity.status(HttpStatus.OK).body(updatedComment);

        } catch (IllegalArgumentException e) {
            // If validation fails, return a bad request status with an error message
            return ResponseEntity.badRequest().body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            // Return a 500 internal server error status in case of any unexpected issue
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while updating the comment.");
        }
    }
}
