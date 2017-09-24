package com.book.api.book.repository;

import com.book.api.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findByGenreId(Pageable pageable, Long genreId);

    Page<Book> findByAuthorId(Pageable pageable, Long authorId);
}
