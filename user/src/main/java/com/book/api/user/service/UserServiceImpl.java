package com.book.api.user.service;

import com.book.api.user.model.JwtToken;
import com.book.api.user.model.User;
import com.book.api.user.repository.JwtTokenRepository;
import com.book.api.user.repository.UserRepository;
import com.book.api.user.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtTokenRepository tokenRepository;
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtTokenRepository tokenRepository,
                           TokenAuthenticationService tokenAuthenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User createUser(String userName, String password, String email) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setToken(createToken(user));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }


    private JwtToken createToken(User user) {
        JwtToken token = tokenAuthenticationService.createJwtToken(user);
        return tokenRepository.save(token);
    }
}
