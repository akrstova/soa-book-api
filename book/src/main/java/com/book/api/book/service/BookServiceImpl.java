package com.book.api.book.service;

import com.book.api.book.model.Book;
import com.book.api.book.repository.BookRepository;
import com.book.api.book.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private SearchRepository searchRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, SearchRepository searchRepository) {
        this.bookRepository = bookRepository;
        this.searchRepository = searchRepository;
    }

    @Override
    public List<Book> searchBooks(String query) {
        return searchRepository.searchPhrase(Book.class, query, "name", "description");
    }

    @Override
    public Book getBook(Long bookId) {
        return bookRepository.findOne(bookId);
    }

    @Override
    public Book updateBook(
            Long bookId,
            String name,
            Integer yearPublished,
            String description,
            Integer numPages,
            Long genreId,
            Long authorId
    ) {
        Book book = bookRepository.findOne(bookId);
        book.setName(name);
        book.setYearPublished(yearPublished);
        book.setDescription(description);
        book.setNumPages(numPages);
        book.setGenreId(genreId);
        book.setAuthorId(authorId);
        return bookRepository.save(book);
    }

    @Override
    public Book createBook(
            String name,
            Integer yearPublished,
            String description,
            Integer numPages,
            Long genreId,
            Long authorId
    ) {
        Book book = new Book();
        book.setName(name);
        book.setYearPublished(yearPublished);
        book.setDescription(description);
        book.setNumPages(numPages);
        book.setGenreId(genreId);
        book.setAuthorId(authorId);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.delete(bookId);
    }

    @Override
    public Page<Book> findByGenreId(Integer page, Integer size, Long genreId) {
        return bookRepository.findByGenreId(new PageRequest(page, size), genreId);
    }

    @Override
    public Page<Book> findByAuthorId(Integer page, Integer size, Long authorId) {
        return bookRepository.findByAuthorId(new PageRequest(page, size), authorId);
    }

    @Override
    public Page<Book> searchBooksPaged(Integer page, Integer size) {
        return bookRepository.findAll(new PageRequest(page, size));
    }
}
