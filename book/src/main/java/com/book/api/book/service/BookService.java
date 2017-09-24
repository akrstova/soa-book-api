package com.book.api.book.service;

import com.book.api.book.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    List<Book> searchBooks(String query);

    Book getBook(Long bookId);

    Book updateBook(
            Long bookId,
            String name,
            Integer yearPublished,
            String description,
            Integer numPages,
            Long genreId,
            Long authorId
    );

    Book createBook(
            String name,
            Integer yearPublished,
            String description,
            Integer numPages,
            Long genreId,
            Long authorId
    );

    void deleteBook(Long bookId);

    Page<Book> findByGenreId(Integer page, Integer size, Long genreId);

    Page<Book> findByAuthorId(Integer page, Integer size, Long authorId);

    Page<Book> searchBooksPaged(Integer page, Integer size);
}
