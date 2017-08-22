package com.book.api.genre.service;

import com.book.api.genre.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre>  findAll();

    Genre findOne(Long id);

    void createGenre(String name);

    void deleteGenre(Long id);

    Genre updateGenre(Long id, String name);
}
