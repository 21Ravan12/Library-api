package com.example.library_api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library_api.model.Comment;

// This interface extends JpaRepository to perform CRUD operations for the Comment entity
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Method to find all comments associated with a specific book, sorted by the given criteria
    List<Comment> findByBookId(Integer bookId, Sort sort);

    // Method to find all comments associated with a specific user, sorted by the given criteria
    List<Comment> findByUserId(Integer userId, Sort sort);

    // Method to find comments containing a specific word (case-insensitive) in their content, sorted by the given criteria
    List<Comment> findByContentContainingIgnoreCase(String content, Sort sort);

    // Method to find comments created at a specific date, sorted by the given criteria
    List<Comment> findByCreatedAt(Date createdAt, Sort sort);

    // Method to find a comment by its ID, sorted by the given criteria (although it's just one comment)
    List<Comment> findById(Integer id, Sort sort);

    // Method to find the latest 5 comments associated with a specific book, sorted by creation date in descending order
    List<Comment> findTop5ByBookIdOrderByCreatedAtDesc(Long bookId);
}
