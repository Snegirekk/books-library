package com.snegirekk.books_library.review.dto;

import java.time.LocalDateTime;

public class ExtendedReviewDto extends ReviewDto {

    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ExtendedReviewDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
