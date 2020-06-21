package com.snegirekk.books_library.core.Repository;

import com.snegirekk.books_library.core.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
