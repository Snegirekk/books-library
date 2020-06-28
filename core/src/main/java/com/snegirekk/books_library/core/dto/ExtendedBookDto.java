package com.snegirekk.books_library.core.dto;

import java.util.List;

public class ExtendedBookDto extends BookDto {

    private boolean available;
    private List<ReviewDto> lastTwoReviews;

    public boolean isAvailable() {
        return available;
    }

    public ExtendedBookDto setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    public List<ReviewDto> getLastTwoReviews() {
        return lastTwoReviews;
    }

    public ExtendedBookDto setLastTwoReviews(List<ReviewDto> lastTwoReviews) {
        this.lastTwoReviews = lastTwoReviews;
        return this;
    }
}
