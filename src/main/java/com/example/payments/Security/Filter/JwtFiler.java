package com.example.payments.Security.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFiler extends OncePerRequestFilter {
    private String jwtToken;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(servletRequest , servletResponse);
            return;
        }
        jwtToken = authHeader.substring(7);

    }
}
