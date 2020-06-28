package com.snegirekk.books_library.review.dto;

import java.util.List;

public class ConstraintViolationListDto {

    private final int statusCode;
    private final List<ConstraintViolationDto> violations;

    public ConstraintViolationListDto(int statusCode, List<ConstraintViolationDto> violations) {
        this.statusCode = statusCode;
        this.violations = violations;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return "Validation is failed";
    }

    public List<ConstraintViolationDto> getViolations() {
        return violations;
    }
}
