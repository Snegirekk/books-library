package com.snegirekk.books_library.core.api_client.dto;

import com.snegirekk.books_library.core.dto.ReviewDto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class NewReviewDto extends ReviewDto {

    @NotNull
    private UUID bookId;

    public UUID getBookId() {
        return bookId;
    }

    public NewReviewDto setBookId(UUID bookId) {
        this.bookId = bookId;
        return this;
    }
}
