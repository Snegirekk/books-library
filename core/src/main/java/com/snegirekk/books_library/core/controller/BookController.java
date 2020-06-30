package com.snegirekk.books_library.core.controller;

import com.snegirekk.books_library.core.dto.BookDto;
import com.snegirekk.books_library.core.dto.ExtendedBookDto;
import com.snegirekk.books_library.core.dto.PageDto;
import com.snegirekk.books_library.core.exception.BookNotFoundException;
import com.snegirekk.books_library.core.exception.BookStateException;
import com.snegirekk.books_library.core.exception.InvalidPageNumberException;
import com.snegirekk.books_library.core.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class BookController extends V1ApiController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/book")
    public PageDto<BookDto> listBooks(HttpServletRequest request, Pageable pageRequest) throws InvalidPageNumberException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        return bookService.getPage(pageRequest);
    }

    @GetMapping(path = "/book/{bookId}")
    public BookDto getBook(HttpServletRequest request, @PathVariable UUID bookId) throws BookNotFoundException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        return bookService.getBook(bookId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/book")
    public ExtendedBookDto createBook(HttpServletRequest request, @RequestBody @Valid BookDto bookDto) {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        return bookService.createBook(bookDto);
    }

    @DeleteMapping(path = "/book/{bookId}")
    public ResponseEntity<Object> deleteBook(HttpServletRequest request, @PathVariable UUID bookId) throws BookNotFoundException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/book/take/{bookId}")
    public ResponseEntity<Object> switchStateToUnavailable(
            HttpServletRequest request,
            @PathVariable UUID bookId
    ) throws BookStateException, BookNotFoundException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        bookService.setBookState(bookId, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/book/return/{bookId}")
    public ResponseEntity<Object> switchStateToAvailable(
            HttpServletRequest request,
            @PathVariable UUID bookId
    ) throws BookStateException, BookNotFoundException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        bookService.setBookState(bookId, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/book/{bookId}")
    public ExtendedBookDto updateBook(
            HttpServletRequest request,
            @RequestBody @Valid BookDto bookDto,
            @PathVariable UUID bookId
    ) throws BookNotFoundException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        bookDto.setId(bookId);
        return bookService.updateBook(bookDto);
    }
}
