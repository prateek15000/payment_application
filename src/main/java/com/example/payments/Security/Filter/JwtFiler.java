package com.example.payments.Security.Filter;

import com.example.payments.Security.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFiler extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String jwtToken;
        final String userEmail;
        final String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(servletRequest , servletResponse);
            return;
        }
        jwtToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwtToken , userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(servletRequest)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(servletRequest , servletResponse);
    }
}
