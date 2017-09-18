package com.book.api.user.security;

import com.book.api.user.model.JwtToken;
import com.book.api.user.model.User;
import com.book.api.user.repository.JwtTokenRepository;
import com.book.api.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.util.Collections.emptyList;

@Service
public class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private static final String SECRET = "SOA_BOOK_API_SECRET";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenRepository tokenRepository;

    // replace the old token after you log in anew
    void addAuthentication(HttpServletResponse res, String username) {
        User user = userRepository.findByUserName(username);
        JwtToken token = createJwtToken(user);
        tokenRepository.save(token);
        res.addHeader(HEADER_STRING,  token.getTokenPayload());
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String userName = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            User user = userRepository.findByUserName(userName);

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), emptyList()) :
                    null;
        }
        return null;
    }

    public JwtToken createJwtToken(User user) {
        Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String JWT = Jwts.builder()
                .setSubject(user.getUserName())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        JwtToken token = user.getToken();
        token.setTokenPayload(TOKEN_PREFIX + " " + JWT);
        token.setExpiryDate(LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
        return token;
    }
}

