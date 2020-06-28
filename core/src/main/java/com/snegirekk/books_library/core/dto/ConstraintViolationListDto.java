package com.snegirekk.books_library.core.dto;

import java.util.List;

public class ConstraintViolationListDto {

    private final int statusCode;
    private final List<ConstraintsViolationDto> violations;

    public ConstraintViolationListDto(int statusCode, List<ConstraintsViolationDto> violations) {
        this.statusCode = statusCode;
        this.violations = violations;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return "Validation is failed";
    }

    public List<ConstraintsViolationDto> getViolations() {
        return violations;
    }
}
