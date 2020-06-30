package com.snegirekk.books_library.core.controller;

import com.snegirekk.books_library.core.api_client.ReviewApiClient;
import com.snegirekk.books_library.core.dto.ExtendedReviewDto;
import com.snegirekk.books_library.core.dto.PageDto;
import com.snegirekk.books_library.core.dto.ReviewDto;
import com.snegirekk.books_library.core.exception.BookNotFoundException;
import com.snegirekk.books_library.core.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ReviewController extends V1ApiController {

    private final BookService bookService;
    private final ReviewApiClient reviewApiClient;

    public ReviewController(BookService bookService, ReviewApiClient reviewApiClient) {
        this.bookService = bookService;
        this.reviewApiClient = reviewApiClient;
    }

    @GetMapping(path = "/review/{reviewId}")
    public ExtendedReviewDto getReview(HttpServletRequest request, @PathVariable UUID reviewId) {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());
        return reviewApiClient.retrieveReviewById(reviewId);
    }

    @GetMapping(path = "/book/{bookId}/review")
    public PageDto<ReviewDto> listReviews(HttpServletRequest request, @PathVariable UUID bookId, Pageable pageRequest) throws BookNotFoundException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());

        if (!bookService.isBookExist(bookId)) {
            throw new BookNotFoundException(String.format("Cannot find book with ID \"%s\".", bookId));
        }

        return reviewApiClient.retrieveReviewsPageByBookId(
                bookId,
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                pageRequest.getSort()
        );
    }

    @PostMapping(path = "/book/{bookId}/review")
    public ExtendedReviewDto createReview(
            HttpServletRequest request,
            @PathVariable UUID bookId,
            @RequestBody @Valid ReviewDto reviewDto
    ) throws BookNotFoundException {
        logger.info("{} {}", request.getMethod(), request.getRequestURL());

        if (!bookService.isBookExist(bookId)) {
            throw new BookNotFoundException(String.format("Cannot find book with ID \"%s\".", bookId));
        }

        return reviewApiClient.createReview(bookId, reviewDto);
    }
}
