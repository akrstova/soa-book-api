package com.book.api.rating.converter;

import com.book.api.rating.model.Rating;
import com.book.api.rating.model.RatingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RatingToRatingDtoConverter implements Converter<Rating, RatingDto>{

    @Override
    public RatingDto convert(Rating rating) {
        RatingDto dto = new RatingDto();
        dto.setBookId(rating.getBookId());
        dto.setAverageGrade(rating.getAverageGrade());
        return dto;
    }
}
