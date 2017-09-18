package com.book.api.user.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JwtTokenDto {

    @NotNull
    private String tokenPayload;

    @NotNull
    private String expiryDate;
}
