package com.snegirekk.books_library.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.snegirekk.books_library.core"})
public class BooksLibraryCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksLibraryCoreApplication.class, args);
    }

}
