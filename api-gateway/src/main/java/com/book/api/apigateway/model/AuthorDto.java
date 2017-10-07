package com.book.api.apigateway.model;

import lombok.Data;

@Data
public class AuthorDto {

    private Long id;
    private String name;
    private String surname;
    private String born;
    private String website;
}
