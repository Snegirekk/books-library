package com.snegirekk.books_library.core.dto;

import javax.validation.constraints.NotBlank;

public class ReviewDto {

    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    public ReviewDto setText(String text) {
        this.text = text;
        return this;
    }
}
