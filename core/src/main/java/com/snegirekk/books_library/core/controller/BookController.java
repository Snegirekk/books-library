package com.snegirekk.books_library.core.controller;

import com.snegirekk.books_library.core.Repository.BookRepository;
import com.snegirekk.books_library.core.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BookController {

    private BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "/book")
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/book/{bookId}")
    public Book getBook(@PathVariable UUID bookId) {
        return bookRepository.findById(bookId).get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/book")
    public Book createBook(@RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @DeleteMapping(path = "/book/{bookId}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID bookId) {
        bookRepository.deleteById(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
