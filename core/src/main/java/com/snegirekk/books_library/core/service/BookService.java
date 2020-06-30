package com.snegirekk.books_library.core.service;

import com.snegirekk.books_library.core.api_client.ReviewApiClient;
import com.snegirekk.books_library.core.dto.ReviewDto;
import com.snegirekk.books_library.core.repository.BookRepository;
import com.snegirekk.books_library.core.dto.BookDto;
import com.snegirekk.books_library.core.dto.ExtendedBookDto;
import com.snegirekk.books_library.core.dto.PageDto;
import com.snegirekk.books_library.core.entity.Book;
import com.snegirekk.books_library.core.exception.BookNotFoundException;
import com.snegirekk.books_library.core.exception.BookStateException;
import com.snegirekk.books_library.core.exception.InvalidPageNumberException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper mapper;
    private final ReviewApiClient reviewApiClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper mapper, ReviewApiClient reviewApiClient) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.reviewApiClient = reviewApiClient;
    }

    public PageDto<BookDto> getPage(Pageable pageRequest) throws InvalidPageNumberException {
        Page<Book> page = bookRepository.findAll(pageRequest);

        // Even when "one-indexed-parameters" configured to "true" internally Page<> starts pages counting from zero
        if (page.getTotalPages() < page.getNumber() || page.getNumber() < 0) {
            throw new InvalidPageNumberException(String.format("Cannot find page with number \"%d\".", page.getNumber()));
        }

        // TODO: find the way to map generic types automatic
        PageDto<BookDto> pageDto = new PageDto<>();
        pageDto
                .setPage(page.getNumber() + 1)
                .setItemsPerPage(page.getSize())
                .setTotalPages(page.getTotalPages());

        for (Book book: page) {
            pageDto.addItem(mapper.map(book, BookDto.class));
        }

        return pageDto;
    }

    public BookDto getBook(UUID id) throws BookNotFoundException {
        ExtendedBookDto bookDto = mapper.map(loadBookFromDb(id), ExtendedBookDto.class);

        try {
            PageDto<ReviewDto> reviewsPage = reviewApiClient
                    .retrieveReviewsPageByBookId(id, 1, 2, Sort.by("createdAt").descending());
            bookDto.setLastTwoReviews(reviewsPage.getItems());
        } catch (RestClientException exception) {
            logger.error("Review API service unavailable.", exception);
        }

        return bookDto;
    }

    public ExtendedBookDto createBook(BookDto bookDto) {
        Book book = mapper.map(bookDto, Book.class);
        bookRepository.save(book);

        return mapper.map(book, ExtendedBookDto.class);
    }

    public void delete(UUID id) throws BookNotFoundException {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new BookNotFoundException(String.format("Cannot find book with ID \"%s\".", id));
        }
    }

    @Transactional
    public void setBookState(UUID bookId, boolean isAvailable) throws BookStateException, BookNotFoundException {
        boolean currentState = bookRepository.isBookAvailableById(bookId)
                .orElseThrow(() -> new BookNotFoundException(String.format("Cannot find book with ID \"%s\".", bookId)));

        if (currentState == isAvailable) {
            throw new BookStateException(String.format("Cannot %s the book with ID \"%s\" because it has been already %s", isAvailable ? "return" : "take", bookId, isAvailable ? "returned" : "taken"));
        }

        bookRepository.updateBookStateById(bookId, isAvailable);
    }

    public ExtendedBookDto updateBook(BookDto bookDto) throws BookNotFoundException {
        Book book = loadBookFromDb(bookDto.getId());
        mapper.map(bookDto, book);
        bookRepository.save(book);

        return mapper.map(book, ExtendedBookDto.class);
    }

    public boolean isBookExist(UUID bookId) {
        return bookRepository.existsById(bookId);
    }

    protected Book loadBookFromDb(UUID id) throws BookNotFoundException {
        try {
            return bookRepository.getOne(id);
        } catch (EntityNotFoundException exception) {
            throw new BookNotFoundException(String.format("Cannot find book with ID \"%s\".", id));
        }
    }
}
