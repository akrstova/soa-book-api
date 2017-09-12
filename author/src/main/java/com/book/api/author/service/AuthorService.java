package com.book.api.author.service;

import com.book.api.author.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findOne(Long id);

    void createAuthor(String name, String surname, String born, String website);

    void deleteAuthor(Long id);

    Author updateAuthor(Long id, String name, String surname, String born, String website);
}
