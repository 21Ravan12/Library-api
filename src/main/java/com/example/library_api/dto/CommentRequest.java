package com.example.library_api.dto;

import java.sql.Date;

public class CommentRequest {
       
    private int id;  // The unique identifier for the comment.
    private int bookId;  // The ID of the book that the comment is associated with.
    private int userId;  // The ID of the user who made the comment.
    private String content;  // The text content of the comment.
    private Date createDate;  // The date when the comment was created.
    private String sortBy;  // The field by which to sort the comments (e.g., createDate).
    private String sortDirection;  // The direction of sorting, either "asc" (ascending) or "desc" (descending).

    // Getter and Setter methods for each field
    public int getBookId() {
        return bookId;  // Returns the ID of the book associated with the comment.
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;  // Sets the ID of the book associated with the comment.
    }

    public int getId() {
        return id;  // Returns the unique identifier of the comment.
    }

    public void setId(int id) {
        this.id = id;  // Sets the unique identifier of the comment.
    }

    public int getUserId() {
        return userId;  // Returns the ID of the user who made the comment.
    }

    public void setUserId(int userId) {
        this.userId = userId;  // Sets the ID of the user who made the comment.
    }

    public Date getCreateDate() {
        return createDate;  // Returns the date when the comment was created.
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;  // Sets the date when the comment was created.
    }

    public String getSortBy() {
        return sortBy;  // Returns the field by which to sort the comments.
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;  // Sets the field by which to sort the comments.
    }

    public String getSortDirection() {
        return sortDirection;  // Returns the direction of sorting (asc or desc).
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;  // Sets the direction of sorting (asc or desc).
    }

    public String getContent() {
        return content;  // Returns the text content of the comment.
    }

    public void setContent(String content) {
        this.content = content;  // Sets the text content of the comment.
    }
}
