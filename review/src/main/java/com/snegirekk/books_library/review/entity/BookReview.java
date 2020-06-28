package com.snegirekk.books_library.review.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book_reviews")
public class BookReview {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private UUID bookId;
    private String text;
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public BookReview setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getBookId() {
        return bookId;
    }

    public BookReview setBookId(UUID bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getText() {
        return text;
    }

    public BookReview setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BookReview setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
