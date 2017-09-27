package com.book.api.userpreferences.repository;

import com.book.api.userpreferences.model.UserPreference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepository extends PagingAndSortingRepository<UserPreference, Long> {

    Page<UserPreference> findByUserId(Pageable pageable, Long userId);
}
