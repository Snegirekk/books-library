package com.snegirekk.books_library.core.repository;

import com.snegirekk.books_library.core.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("select b.available from Book b where b.id = ?1")
    Optional<Boolean> isBookAvailableById(UUID id);

    @Query("update Book b set b.available = :available where b.id = :id")
    @Modifying
    void updateBookStateById(@Param("id") UUID id, @Param("available") boolean available);
}
