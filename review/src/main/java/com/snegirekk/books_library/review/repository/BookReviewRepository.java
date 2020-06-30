package com.snegirekk.books_library.review.repository;

import com.snegirekk.books_library.review.entity.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookReviewRepository extends JpaRepository<BookReview, UUID> {

    Page<BookReview> findByBookId(UUID bookId, Pageable pageRequest);
}
