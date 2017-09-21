package com.book.api.zuul.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.util.Collections.emptyList;

class TokenAuthenticationService {
    private static final String SECRET = "SOA_BOOK_API_SECRET";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null && checkIfTokenIsNotExpired(token)) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }

    private static boolean checkIfTokenIsNotExpired(String token) {
        if (token != null) {
            Date expiryDate = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody().getExpiration();

            LocalDateTime expiryDateTime = LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault());
            return !expiryDateTime.isBefore(LocalDateTime.now());
        }
        return false;
    }
}