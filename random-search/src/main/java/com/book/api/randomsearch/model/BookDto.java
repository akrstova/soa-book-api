package com.book.api.randomsearch.model;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String name;
    private Integer yearPublished;
    private String description;
    private Integer numPages;
    private GenreDto genre;
    private AuthorDto author;
    private RatingDto rating;
}
