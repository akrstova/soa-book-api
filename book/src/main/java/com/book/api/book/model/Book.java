package com.book.api.book.model;

import lombok.Data;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "books")
@Indexed
@AnalyzerDef(name = "bookMicroserviceAnalyser",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class)
        })
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    @NotNull
    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Analyzer(definition = "bookMicroserviceAnalyser")
    @Boost(2f)
    private String name;

    @NotNull
    private Integer yearPublished;

    @Column(length = 1000)
    @NotNull
    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Analyzer(definition = "bookMicroserviceAnalyser")
    @Boost(0.5f)
    private String description;

    @NotNull
    private Integer numPages;

    @NotNull
    private Long genreId;

    @NotNull
    private Long authorId;
}
