package com.coding.elia.infrastructure.filter;

import com.coding.elia.application.context.UserEmailContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String userEmail = req.getHeader("email");
        UserEmailContext.setUserContext(userEmail);

        try {
            chain.doFilter(request, response);
        } finally {
            UserEmailContext.clear();
        }
    }
}
