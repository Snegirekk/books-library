package com.snegirekk.books_library.review.controller;

import com.snegirekk.books_library.review.dto.ExtendedReviewDto;
import com.snegirekk.books_library.review.dto.PageDto;
import com.snegirekk.books_library.review.dto.ReviewDto;
import com.snegirekk.books_library.review.entity.BookReview;
import com.snegirekk.books_library.review.exception.InvalidPageNumberException;
import com.snegirekk.books_library.review.repository.BookReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@RestController
public class BookReviewController extends V1ApiController {

    private final ModelMapper mapper;
    private final BookReviewRepository bookReviewRepository;

    public BookReviewController(ModelMapper modelMapper, BookReviewRepository bookReviewRepository) {
        this.mapper = modelMapper;
        this.bookReviewRepository = bookReviewRepository;
    }

    @GetMapping(path = "/review/{reviewId}")
    public ExtendedReviewDto getReview(@PathVariable UUID reviewId) {
        return mapper.map(bookReviewRepository.getOne(reviewId), ExtendedReviewDto.class);
    }

    @PostMapping(path = "/review")
    public ExtendedReviewDto createReview(@Valid @RequestBody ReviewDto reviewDto) {
        BookReview review = mapper.map(reviewDto, BookReview.class);
        bookReviewRepository.save(review);
        return mapper.map(review, ExtendedReviewDto.class);
    }

    @GetMapping(path = "/review")
    public PageDto<ReviewDto> listReviews(@NotBlank @RequestParam UUID bookId, Pageable pageRequest) throws InvalidPageNumberException {
        Page<BookReview> page = bookReviewRepository.findAllByBookId(bookId, pageRequest);

        // Even when "one-indexed-parameters" configured to "true" internally Page<> starts pages counting from zero
        if (page.getTotalPages() < page.getNumber() || page.getNumber() < 0) {
            throw new InvalidPageNumberException(String.format("Cannot find page with number \"%d\".", page.getNumber()));
        }

        // TODO: find the way to map generic types automatic
        PageDto<ReviewDto> pageDto = new PageDto<>();
        pageDto
                .setPage(page.getNumber() + 1)
                .setItemsPerPage(page.getSize())
                .setTotalPages(page.getTotalPages());

        for (BookReview review: page) {
            pageDto.addItem(mapper.map(review, ReviewDto.class));
        }

        return pageDto;
    }
}
