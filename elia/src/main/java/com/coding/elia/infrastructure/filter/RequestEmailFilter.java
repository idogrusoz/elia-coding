package com.coding.elia.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class RequestEmailFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String userEmail = request.getHeader("email");
        if (userEmail == null || userEmail.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "email header is required");
            return;
        }
        logger.info("Successfully authenticated user  " +
                userEmail);
        filterChain.doFilter(request, response);
    }
}