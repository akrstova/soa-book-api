package com.book.api.author.service;

import com.book.api.author.model.Author;
import com.book.api.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author findOne(Long id) {
        return authorRepository.findOne(id);
    }

    @Override
    public void createAuthor(String name, String surname, String born, String website) {
        Author author = new Author();
        author.setName(name);
        author.setSurname(surname);
        author.setBorn(born);
        author.setWebsite(website);

        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.delete(id);
    }

    @Override
    public Author updateAuthor(Long id, String name, String surname, String born, String website) {
        Author author = authorRepository.findOne(id);
        author.setName(name);
        author.setSurname(surname);
        author.setBorn(born);
        author.setWebsite(website);

        return authorRepository.save(author);
    }
}
