package com.book.api.book.controllers;

import com.book.api.book.model.Book;
import com.book.api.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/books" ,produces = "application/json")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
        Book book = bookService.getBook(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
        Book savedBook = bookService.createBook(
                book.getName(),
                book.getYearPublished(),
                book.getDescription(),
                book.getNumPages(),
                book.getGenreId(),
                book.getAuthorId()
        );

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody @Valid Book book, @PathVariable Long bookId) {
        Book updatedBook = bookService.updateBook(
                bookId,
                book.getName(),
                book.getYearPublished(),
                book.getDescription(),
                book.getNumPages(),
                book.getGenreId(),
                book.getAuthorId()
        );

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/genre/{genreId}")
    public ResponseEntity<Page<Book>> findByGenreId(
            @PathVariable Long genreId, @RequestParam Integer page, @RequestParam Integer size) {

        Page<Book> bookPage = bookService.findByGenreId(page, size, genreId);
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/author/{authorId}")
    public ResponseEntity<Page<Book>> findByAuthorId(
            @PathVariable Long authorId, @RequestParam Integer page, @RequestParam Integer size) {

        Page<Book> bookPage = bookService.findByAuthorId(page, size, authorId);
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        List<Book> books = bookService.searchBooks(query);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooksPaged(@RequestParam Integer page, @RequestParam Integer size) {
        Page<Book> bookPage = bookService.searchBooksPaged(page, size);
        return new ResponseEntity<>(bookPage.getContent(), HttpStatus.OK);
    }
}

