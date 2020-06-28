package com.snegirekk.books_library.review.dto;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class ReviewDto {

    private UUID id;

    @NotBlank
    private UUID bookId;

    @NotBlank
    private String text;

    public UUID getId() {
        return id;
    }

    public ReviewDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getBookId() {
        return bookId;
    }

    public ReviewDto setBookId(UUID bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getText() {
        return text;
    }

    public ReviewDto setText(String text) {
        this.text = text;
        return this;
    }
}
