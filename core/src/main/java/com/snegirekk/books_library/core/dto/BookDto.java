package com.snegirekk.books_library.core.dto;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class BookDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String author;

    public UUID getId() {
        return id;
    }

    public BookDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BookDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookDto setAuthor(String author) {
        this.author = author;
        return this;
    }
}
