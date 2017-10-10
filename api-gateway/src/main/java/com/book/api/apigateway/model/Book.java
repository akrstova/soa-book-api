package com.book.api.apigateway.model;

import lombok.Data;

@Data
public class Book {

    private Long id;
    private String name;
    private Integer yearPublished;
    private String description;
    private Integer numPages;
    private GenreDto genre;
    private AuthorDto author;
}
