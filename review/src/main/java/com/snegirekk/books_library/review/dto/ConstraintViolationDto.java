package com.snegirekk.books_library.review.dto;

import java.io.Serializable;

public class ConstraintViolationDto implements Serializable {

    private final String path;
    private final String violation;
    private final String actualValue;

    public ConstraintViolationDto(String violation, String path, String actualValue) {
        this.violation = violation;
        this.path = path;
        this.actualValue = actualValue;
    }

    public String getPath() {
        return path;
    }

    public String getViolation() {
        return violation;
    }

    public String getActualValue() {
        return actualValue;
    }

    @Override
    public String toString() {
        return "{" +
                "\"path\": \"" + path + '\"' +
                ", \"violation\": \"" + violation + '\"' +
                ", \"actual_value\": \"" + actualValue + '\"' +
                '}';
    }
}
