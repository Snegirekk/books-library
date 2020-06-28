package com.snegirekk.books_library.core.dto;

public class ErrorDto {

    private final int statusCode;
    private final String message;

    public ErrorDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
