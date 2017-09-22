package com.book.api.rating.controllers;

import com.book.api.rating.converter.RatingToRatingDtoConverter;
import com.book.api.rating.model.Rating;
import com.book.api.rating.model.RatingDto;
import com.book.api.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.*;


@RestController
@RequestMapping(value = "/ratings", produces = "application/json")
public class RatingController {

    private RatingService ratingService;
    private RatingToRatingDtoConverter ratingDtoConverter;

    @Autowired
    public RatingController(RatingService ratingService, RatingToRatingDtoConverter ratingDtoConverter) {
        this.ratingService = ratingService;
        this.ratingDtoConverter = ratingDtoConverter;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
    public ResponseEntity<RatingDto> getBookRating(@PathVariable Long bookId) {
        Rating rating = ratingService.getBookRating(bookId);
        RatingDto dto = ratingDtoConverter.convert(rating);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long bookId) {
        ratingService.deleteRating(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{bookId}")
    public ResponseEntity<RatingDto> rateBook(@PathVariable Long bookId, @RequestParam Integer grade) {
        Rating rating = ratingService.rateBook(grade, bookId);
        RatingDto dto = ratingDtoConverter.convert(rating);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RatingDto>> searchByAverageGrade(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam BigDecimal grade) {

        Page<Rating> ratings = ratingService.searchByAverageGrade(pageNumber, pageSize, grade);
        List<RatingDto> dtos = ratings.getContent()
                .stream()
                .map(ratingDtoConverter::convert)
                .collect(toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
