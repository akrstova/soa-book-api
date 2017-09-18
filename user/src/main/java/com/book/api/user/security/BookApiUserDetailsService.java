package com.book.api.user.security;

import com.book.api.user.model.User;
import com.book.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import static java.util.Collections.emptyList;

@Service
public class BookApiUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public BookApiUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(), user.getPassword(), emptyList()
            );
        } else {
            return new org.springframework.security.core.userdetails.User(
                    "anonymous", "", emptyList()
            );
        }
    }
}
