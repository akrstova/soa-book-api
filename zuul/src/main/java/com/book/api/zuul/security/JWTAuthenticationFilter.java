package com.book.api.zuul.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private static final String REGISTER_ENDPOINT = "/book-api/users/register";
    private static final String LOGIN_ENDPOINT = "/book-api/login";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (!checkEndpointIsPermittedWithoutAuthentication(httpServletRequest)) {
            Authentication authentication = TokenAuthenticationService.getAuthentication(httpServletRequest);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendError(401, "Unauthorized");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean checkEndpointIsPermittedWithoutAuthentication(HttpServletRequest request) {
        return LOGIN_ENDPOINT.equals(request.getRequestURI()) ||
                REGISTER_ENDPOINT.equals(request.getRequestURI());
    }
}