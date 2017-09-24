package com.book.api.rating.service;

import com.book.api.rating.model.Rating;
import com.book.api.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RatingServiceImpl implements RatingService{

    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating rateBook(Integer grade, Long bookId) {
       Rating rating = ratingRepository.findByBookId(bookId);
       if (rating != null) {
           rating.setNumRatings(rating.getNumRatings() + 1);
           rating.setSumGrades(rating.getSumGrades() + grade);
           rating.setAverageGrade(calculateAverageGrade(rating));
       } else {
           rating = new Rating();
           rating.setBookId(bookId);
           rating.setNumRatings(1);
           rating.setSumGrades(grade);
           rating.setAverageGrade(new BigDecimal(grade));
       }
       return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long bookId) {
        ratingRepository.deleteByBookId(bookId);
    }

    @Override
    public Page<Rating> searchByAverageGrade(Integer pageNumber, Integer pageSize, BigDecimal grade) {
        return ratingRepository.findByAverageGrade(new PageRequest(pageNumber, pageSize), grade);
    }

    @Override
    public Rating getBookRating(Long bookId) {
        return ratingRepository.findByBookId(bookId);
    }

    private BigDecimal calculateAverageGrade(Rating rating) {
        Integer totalRatings = rating.getNumRatings();
        Integer sumGrades = rating.getSumGrades();
        return new BigDecimal(sumGrades / totalRatings);
    }
}
