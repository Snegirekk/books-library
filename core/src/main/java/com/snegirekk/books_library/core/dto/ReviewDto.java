package com.snegirekk.books_library.core.dto;

public class ReviewDto {

    private String text;

    public String getText() {
        return text;
    }

    public ReviewDto setText(String text) {
        this.text = text;
        return this;
    }
}
