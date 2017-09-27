package com.book.api.userpreferences.service;

import com.book.api.userpreferences.model.UserPreference;
import com.book.api.userpreferences.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    public UserPreferenceServiceImpl(UserPreferenceRepository userPreferenceRepository) {
        this.userPreferenceRepository = userPreferenceRepository;
    }

    @Override
    public List<UserPreference> getUserPreferences(Integer page, Integer size, Long userId) {
        return userPreferenceRepository.findByUserId(new PageRequest(page, size), userId).getContent();
    }

    @Override
    public UserPreference createUserPreference(Long userId, Long authorId, Long genreId) {
        UserPreference userPreference = new UserPreference();
        userPreference.setUserId(userId);
        userPreference.setAuthorId(authorId);
        userPreference.setGenreId(genreId);

        return userPreferenceRepository.save(userPreference);
    }

    @Override
    public UserPreference updateUserPreference(Long id, Long userId, Long authorId, Long genreId) {
        UserPreference userPreference = userPreferenceRepository.findOne(id);
        userPreference.setUserId(userId);
        userPreference.setAuthorId(authorId);
        userPreference.setGenreId(genreId);

        return userPreferenceRepository.save(userPreference);
    }

    @Override
    public void deleteUserPreference(Long id) {
        userPreferenceRepository.delete(id);
    }
}
