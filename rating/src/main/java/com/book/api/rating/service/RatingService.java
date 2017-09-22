package com.book.api.rating.service;

import com.book.api.rating.model.Rating;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface RatingService {

    Rating rateBook(Integer grade, Long bookId);

    void deleteRating(Long bookId);

    Page<Rating> searchByAverageGrade(Integer pageNumber, Integer pageSize, BigDecimal grade);

    Rating getBookRating(Long bookId);
}
