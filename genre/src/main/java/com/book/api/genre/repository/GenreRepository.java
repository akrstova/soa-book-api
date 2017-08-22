package com.book.api.genre.repository;

import com.book.api.genre.model.Genre;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

    Genre findByName(String name);
}
