package com.snegirekk.books_library.review.exception;

public class ReviewNotFoundException extends PersistenceException {

    public ReviewNotFoundException(String s) {
        super(s);
    }
}
