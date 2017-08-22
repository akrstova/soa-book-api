package com.book.api.genre.service;

import com.book.api.genre.model.Genre;
import com.book.api.genre.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return (List<Genre>) genreRepository.findAll();
    }

    @Override
    public Genre findOne(Long id) {
        return genreRepository.findOne(id);
    }

    @Override
    public void createGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.delete(id);
    }

    @Override
    public Genre updateGenre(Long id, String name) {
        Genre genre = genreRepository.findOne(id);
        genre.setName(name);
        return genreRepository.save(genre);
    }
}
