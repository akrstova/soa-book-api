package com.book.api.user.controllers;


import com.book.api.user.converter.JwtTokenToJwtTokenDtoConverter;
import com.book.api.user.converter.UserToUserDtoConverter;
import com.book.api.user.model.JwtTokenDto;
import com.book.api.user.model.User;
import com.book.api.user.model.UserDto;
import com.book.api.user.security.TokenAuthenticationService;
import com.book.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserToUserDtoConverter userToUserDtoConverter;

    @Autowired
    private JwtTokenToJwtTokenDtoConverter jwtTokenDtoConverter;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<JwtTokenDto> createUser(@RequestBody @Valid User user) {
        User savedUser = userService.createUser(user.getUserName(), user.getPassword(), user.getEmail());
        JwtTokenDto tokenDto = jwtTokenDtoConverter.convert(savedUser.getToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TokenAuthenticationService.HEADER_STRING,
                TokenAuthenticationService.TOKEN_PREFIX + " " + tokenDto.getTokenPayload());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.findAll();
        List<UserDto> userDtoList = users.stream()
                .map(user -> userToUserDtoConverter.convert(user))
                .collect(toList());
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
}
