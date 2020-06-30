package com.snegirekk.books_library.core.dto;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class ReviewDto {

    private UUID id;

    @NotBlank
    private String text;

    public UUID getId() {
        return id;
    }

    public ReviewDto setId(UUID id) {
        this.id = id;
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
