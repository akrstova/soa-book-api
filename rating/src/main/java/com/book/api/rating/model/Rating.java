package com.book.api.rating.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer sumGrades;

    @NotNull
    private Integer numRatings;

    @NotNull
    @Range(min = 1, max = 5)
    private BigDecimal averageGrade;

    @NotNull
    private Long bookId;
}
