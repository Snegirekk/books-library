package com.snegirekk.books_library.core.controller;

import com.snegirekk.books_library.core.dto.ConstraintViolationListDto;
import com.snegirekk.books_library.core.dto.ConstraintsViolationDto;
import com.snegirekk.books_library.core.dto.ErrorDto;
import com.snegirekk.books_library.core.exception.BookLibraryException;
import com.snegirekk.books_library.core.exception.BookNotFoundException;
import com.snegirekk.books_library.core.exception.InvalidPageNumberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.value;

@RestControllerAdvice
@RequestMapping(path = "/api/v1")
public class V1ApiController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(NoHandlerFoundException exception) {
        String message = String.format("Route '%s %s' not found.", exception.getHttpMethod(), exception.getRequestURL());
        ErrorDto error = new ErrorDto(message, HttpStatus.NOT_FOUND.value());
        logger.warn(message);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorDto> handleNotReadableMessage(Exception exception) {
        logger.warn("Bad request occurred", exception);
        return makeBadRequestResponseFromException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ConstraintViolationListDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<ConstraintsViolationDto> violations = result.getAllErrors().stream()
                .map(error -> new ConstraintsViolationDto(
                        error.getDefaultMessage(),
                        ((FieldError) error).getField(),
                        ((FieldError) error).getRejectedValue() == null ? null : ((FieldError) error).getRejectedValue().toString())
                )
                .collect(Collectors.toList());

        logger.warn("Validation is failed: {}", value("violations", violations));

        return new ResponseEntity<>(new ConstraintViolationListDto(HttpStatus.BAD_REQUEST.value(), violations), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookLibraryException.class)
    public ResponseEntity<ErrorDto> handleAppException(BookLibraryException exception) {
        logger.warn("Client-side error occurred", exception);

        if (exception.getClass().equals(BookNotFoundException.class) || exception.getClass().equals(InvalidPageNumberException.class)) {
            ErrorDto error = new ErrorDto(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else {
            ErrorDto error = new ErrorDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorDto> handleRestClientException(RestClientException exception) {
        logger.error("External service unavailable.", exception);

        ErrorDto error = new ErrorDto("External service unavailable.", HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        logger.error("Unexpected exception occurred", exception);

        ErrorDto error = new ErrorDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDto> makeBadRequestResponseFromException(Exception exception) {

        ErrorDto error = new ErrorDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
