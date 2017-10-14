package com.book.api.author.controllers;

import com.book.api.author.model.Author;
import com.book.api.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/authors", produces = "application/json")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<List<Author>> findAll() {
        List<Author> authorList = authorService.findAll();
        return new ResponseEntity<>(authorList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Author> findOne(@PathVariable Long id) {
        Author author = authorService.findOne(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Void> createAuthor(@RequestBody Author author) {
        authorService.createAuthor(author.getName(), author.getBorn(), author.getWebsite());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
        Author updatedAuthor = authorService
                .updateAuthor(id, author.getName(), author.getBorn(), author.getWebsite());
        return new ResponseEntity<>(updatedAuthor, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
