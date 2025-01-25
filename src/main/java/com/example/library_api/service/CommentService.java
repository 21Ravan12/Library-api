package com.example.library_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library_api.dto.CommentRequest;
import com.example.library_api.model.Book;
import com.example.library_api.model.Comment;
import com.example.library_api.model.User;
import com.example.library_api.repository.BookRepository;
import com.example.library_api.repository.CommentRepository;
import com.example.library_api.repository.UserRepository;

@Service
public class CommentService {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    // Method to add a new comment to a book by a user
    public Comment addComment(CommentRequest commentRequest) {
        // Find the book and user by their IDs
        Book book = bookRepository.findById((long) commentRequest.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        User user = userRepository.findById((long) commentRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a new Comment object
        Comment comment = new Comment();
        comment.setBook(book); // Associate the comment with the book
        comment.setUser(user); // Associate the comment with the user
        comment.setContent(commentRequest.getContent()); // Set the content of the comment
        
        // Save the new comment to the database
        return commentRepository.save(comment); 
    }

    // Method to delete an existing comment by its ID
    public void deleteComment(Long id) {
        // Find the comment by its ID
        Comment comment = commentRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Comment with ID " + id + " not found")
        );
        
        // Delete the comment from the database
        commentRepository.delete(comment); 
    }

    // Method to search for comments based on various parameters
    public List<Comment> searchComments(Integer id, String content, java.util.Date createdAt, Integer bookId, Integer userId, 
                                        String sortBy, String sortDirection) {
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
        if (id != null) {
            return commentRepository.findById(id, sort); // Search by comment ID
        } else if (content != null) {
            return commentRepository.findByContentContainingIgnoreCase(content, sort); // Search by comment content
        } else if (createdAt != null) {
            return commentRepository.findByCreatedAt(createdAt, sort); // Search by creation date
        } else if (bookId != null) {
            return commentRepository.findByBookId(bookId, sort); // Search by book ID
        } else if (userId != null) {
            return commentRepository.findByUserId(userId, sort); // Search by user ID
        } else {
            // If no parameters are given, return all comments
            return commentRepository.findAll(sort); 
        }
    }

    // Method to update an existing comment
    public Comment updateComment(CommentRequest commentRequest) {
        // Retrieve the comment to be updated by its ID
        Long commentId = (long) commentRequest.getId();
    
        // Find the comment by its ID in the database
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
    
        // Update the content of the comment if provided
        if (commentRequest.getContent() != null && !commentRequest.getContent().isEmpty()) {
            comment.setContent(commentRequest.getContent()); 
        }
    
        // Update the user associated with the comment if provided
        if (commentRequest.getUserId() > 0) {
            User user = userRepository.findById((long) commentRequest.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + commentRequest.getUserId()));
            comment.setUser(user); // Set the new user
        }
    
        // Update the book associated with the comment if provided
        if (commentRequest.getBookId() > 0) {
            Book newBook = bookRepository.findById((long) commentRequest.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + commentRequest.getBookId()));
            comment.setBook(newBook); // Set the new book
        }
    
        // Update the creation date of the comment if provided
        if (commentRequest.getCreateDate() != null) {
            comment.setCreatedAt(commentRequest.getCreateDate().toLocalDate().atStartOfDay());
        }
    
        // Save the updated comment to the database
        return commentRepository.save(comment); 
    }
}
