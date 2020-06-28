package com.snegirekk.books_library.core.exception;

public class BookNotFoundException extends PersistenceException {

    public BookNotFoundException(String s) {
        super(s);
    }
}
