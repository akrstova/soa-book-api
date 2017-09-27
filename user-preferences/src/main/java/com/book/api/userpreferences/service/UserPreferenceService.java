package com.book.api.userpreferences.service;

import com.book.api.userpreferences.model.UserPreference;

import java.util.List;

public interface UserPreferenceService {

    List<UserPreference> getUserPreferences(Integer page, Integer size, Long userId);

    UserPreference createUserPreference(Long userId, Long authorId, Long genreId);

    UserPreference updateUserPreference(Long id, Long userId, Long authorId, Long genreId);

    void deleteUserPreference(Long id);
}
