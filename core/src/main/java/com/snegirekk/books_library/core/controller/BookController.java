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

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/book")
    public PageDto<BookDto> listBooks(Pageable pageRequest) throws InvalidPageNumberException {
        return bookService.getPage(pageRequest);
    }

    @GetMapping(path = "/book/{bookId}")
    public BookDto getBook(@PathVariable UUID bookId) throws BookNotFoundException {
        return bookService.getBook(bookId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/book")
    public ExtendedBookDto createBook(@RequestBody @Valid BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @DeleteMapping(path = "/book/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID bookId) {
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/book/take/{bookId}")
    public ResponseEntity<Object> switchStateToUnavailable(@PathVariable UUID bookId) throws BookStateException {
        bookService.setBookState(bookId, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/book/return/{bookId}")
    public ResponseEntity<Object> switchStateToAvailable(@PathVariable UUID bookId) throws BookStateException {
        bookService.setBookState(bookId, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/book/{bookId}")
    public ExtendedBookDto updateBook(@RequestBody @Valid BookDto bookDto, @PathVariable UUID bookId) throws BookNotFoundException {
        bookDto.setId(bookId);
        return bookService.updateBook(bookDto);
    }
}
