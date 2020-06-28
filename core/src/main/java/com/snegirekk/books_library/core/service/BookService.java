package com.snegirekk.books_library.core.service;

import com.snegirekk.books_library.core.repository.BookRepository;
import com.snegirekk.books_library.core.dto.BookDto;
import com.snegirekk.books_library.core.dto.ExtendedBookDto;
import com.snegirekk.books_library.core.dto.PageDto;
import com.snegirekk.books_library.core.entity.Book;
import com.snegirekk.books_library.core.exception.BookNotFoundException;
import com.snegirekk.books_library.core.exception.BookStateException;
import com.snegirekk.books_library.core.exception.InvalidPageNumberException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
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
        return mapper.map(loadBookFromDb(id), ExtendedBookDto.class);
    }

    public ExtendedBookDto createBook(BookDto bookDto) {
        Book book = mapper.map(bookDto, Book.class);
        bookRepository.save(book);

        return mapper.map(book, ExtendedBookDto.class);
    }

    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setBookState(UUID bookId, boolean isAvailable) throws BookStateException {
        boolean currentState = bookRepository.isBookAvailableById(bookId);

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

    protected Book loadBookFromDb(UUID id) throws BookNotFoundException {
        try {
            return bookRepository.getOne(id);
        } catch (EntityNotFoundException exception) {
            throw new BookNotFoundException(String.format("Cannot find book with ID \"%s\".", id));
        }
    }
}
