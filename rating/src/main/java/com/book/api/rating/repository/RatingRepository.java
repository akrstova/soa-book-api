package com.book.api.rating.repository;

import com.book.api.rating.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long>{

    Rating findByBookId(Long bookId);

    void deleteByBookId(Long bookId);

    Page<Rating> findByAverageGrade(Pageable pageable, BigDecimal grade);
}
