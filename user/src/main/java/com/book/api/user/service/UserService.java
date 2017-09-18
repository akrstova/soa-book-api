package com.book.api.user.service;

import com.book.api.user.model.User;

import java.util.List;

public interface UserService {

    User findByUserName(String userName);

    User createUser(String userName, String password, String email);

    List<User> findAll();
}
