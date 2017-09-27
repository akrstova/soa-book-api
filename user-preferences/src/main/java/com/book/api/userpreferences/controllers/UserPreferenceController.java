package com.book.api.userpreferences.controllers;

import com.book.api.userpreferences.model.UserPreference;
import com.book.api.userpreferences.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping(value = "/user-preferences", produces = "application/json")
public class UserPreferenceController {

    private UserPreferenceService userPreferenceService;

    @Autowired
    public UserPreferenceController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserPreference>> getUserPreferences(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam Long userId) {

        List<UserPreference> userPreferences = userPreferenceService.getUserPreferences(page, size, userId);
        return ResponseEntity.ok(userPreferences);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteUserPreference(@PathVariable Long id) {
        userPreferenceService.deleteUserPreference(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserPreference> createUserPreference(@RequestBody UserPreference userPreference)
            throws ValidationException {

        // we don't use @Valid because the Id is not obligatory in creating the user-preference
        if (userPreference != null) {
            UserPreference savedUserPreference = userPreferenceService.createUserPreference(
                    userPreference.getUserId(),
                    userPreference.getAuthorId(),
                    userPreference.getGenreId()
            );
            return new ResponseEntity<>(savedUserPreference, HttpStatus.CREATED);
        } else {
            throw new ValidationException("One or more parameters are missing");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<UserPreference> updateUserPreference(@RequestBody @Valid UserPreference userPreference) {
        UserPreference updatedUserPreference = userPreferenceService.updateUserPreference(
                userPreference.getId(),
                userPreference.getUserId(),
                userPreference.getAuthorId(),
                userPreference.getGenreId()
        );

        return ResponseEntity.ok(updatedUserPreference);
    }
}
